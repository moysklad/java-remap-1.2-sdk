package com.lognex.api.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.model.base.Entity;
import com.lognex.api.model.entity.Employee;
import com.lognex.api.response.ApiError;
import com.lognex.api.response.ApiResponse;
import com.lognex.api.response.Context;
import com.lognex.api.util.MetaHrefUtils;
import com.lognex.api.util.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ResponseParser {

    static ApiResponse parse(CloseableHttpResponse response, MSRequest msRequest){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))){
            String body = reader.lines().reduce((a, b) -> a+b).orElse("");
            Type type = typeFromUrl(msRequest.getUrl());

            return new ApiResponse(response.getStatusLine().getStatusCode(),
                    parseErrors(body),
                    parseEntities(body, type),
                    Arrays.asList(response.getAllHeaders()),
                    parseContext(body));
        } catch (IOException e) {
            log.error("Error while reading response from server: ", e);
            throw new RuntimeException(e);
        }
    }


    private static Set<ApiError> parseErrors(String body) throws IOException {
        if (body != null && !body.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(body);
            if (json.has("errors")) {
                Set<ApiError> errors = new HashSet<>();
                ArrayNode errorsNode = (ArrayNode) json.get("errors");
                for (int i = 0; i < errorsNode.size(); ++i) {
                    JsonNode error = errorsNode.get(i);
                    ApiError parsed = mapper.readValue(error.toString(), ApiError.class);
                    errors.add(parsed);
                }
                return errors;
            }
        }
        return Collections.emptySet();
    }

    private static Context parseContext(String body) throws IOException {
        if (body != null && !body.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(body);
            if (json.has("context")) {
                Context context = new Context();
                JsonNode employee = json.get("context").get("employee");
                context.setEmployee(new Employee(MetaHrefUtils.getId(employee.get("meta").get("href").asText())));
                return context;
            }
        }
        return null;
    }


    private static List<Entity> parseEntities(String body, Type type) throws IOException {
        List<Entity> result = new ArrayList<>();
        if (body != null && !body.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(body);
            if (json.has("errors")){
                return result;
            }
            if (json.has("rows")){
                //map multiple
                ArrayNode rows = (ArrayNode) json.get("rows");
                for (int i = 0; i < rows.size(); ++i){
                    result.add(parseJsonObject(rows.get(i)));
                }

            } else if (json.isObject()) {
                // single value
                String entityType = json.has("meta") && json.get("meta").has("type")?
                        json.get("meta").get("type").asText() : type.getApiName();
                Entity entity = ConverterFactory.getConverter(Type.find(entityType).getModelClass()).convert(json.toString());
                result.add(entity);
            }
            return result;
        }
        return result;
    }

    private static Entity parseJsonObject(JsonNode json){
        String type = json.get("meta").get("type").asText();
        return ConverterFactory.getConverter(Type.find(type).getModelClass()).convert(json.toString());
    }

    private static Type typeFromUrl(String url){
        String[] split = url.split("/");
        if (split[6].equals("entity")){
            return Type.find(split[7]);
        }
        return null;
    }

}
