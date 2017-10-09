package com.lognex.api.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.ImmutableList;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.entity.Employee;
import com.lognex.api.util.MetaHrefParser;
import com.lognex.api.util.Type;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Getter
@Slf4j
public class ApiResponse {

    private int status;
    private Set<ApiError> errors;
    private List<? extends AbstractEntity> entities;
    private List<Header> headers;
    private Context context;

    private ApiResponse(int status, Set<ApiError> errors, List<AbstractEntity> entities, List<Header> headers, Context context){
        this.status = status;
        this.errors = errors;
        this.entities = entities;
        this.headers = headers;
        this.context = context;
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    public static ApiResponse produce(CloseableHttpResponse response) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))){
            String body = reader.lines().reduce((a, b) -> a+b).orElse("");

            return new ApiResponse(response.getStatusLine().getStatusCode(),
                    parseErrors(body),
                    parseEntities(body),
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
                Context context = new Context(); // YEEEEE JAVA
                JsonNode employee = json.get("context").get("employee");
                context.setEmployee(new Employee(MetaHrefParser.getId(employee.get("meta").get("href").asText())));
                return context;
            }
        }
        return null;
    }


    private static List<AbstractEntity> parseEntities(String body) throws IOException {
        List<AbstractEntity> result = new ArrayList<>();
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
                String type = json.get("meta").get("type").asText();
                AbstractEntity entity = ConverterFactory.getConverter(Type.valueOf(type).getModelClass()).convert(json.toString());
                result.add(entity);
            }
            return result;
        }
        return result;
    }

    private static AbstractEntity parseJsonObject(JsonNode json){
        String type = json.get("meta").get("type").asText();
        return ConverterFactory.getConverter(Type.valueOf(type).getModelClass()).convert(json.toString());
    }
}
