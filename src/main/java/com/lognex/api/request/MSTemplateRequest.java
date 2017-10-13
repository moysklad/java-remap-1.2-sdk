package com.lognex.api.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.ApiClient;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.converter.base.AbstractEntityConverter;
import com.lognex.api.converter.base.CustomJgenFactory;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.model.base.AbstractEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.lognex.api.util.Constants.APPLICATION_JSON;

@Slf4j
public class MSTemplateRequest extends MSRequest {


    private ObjectMapper mapper = new ObjectMapper();
    private final AbstractEntity entity;

    public <T extends AbstractEntity> MSTemplateRequest(String url, ApiClient client, T entity) {
        super(url, client);
        this.entity = entity;
    }

    @Override
    protected HttpUriRequest buildRequest() {
        HttpPut putRequest = new HttpPut(getUrl());
        putRequest.setHeader("ContentType", APPLICATION_JSON);
        StringEntity entity = null;
        try {
            entity = new StringEntity(convertToJsonBody());
            putRequest.setEntity(entity);
            entity.setContentType(APPLICATION_JSON);
        } catch (UnsupportedEncodingException ignored) {
            log.error("Error while composing create request: ", ignored);
        } catch (IOException e) {
            log.error("Error while serializing entity to json", e);
        }
        return putRequest;
    }

    private String convertToJsonBody() throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            CustomJsonGenerator jsonGenerator = CustomJgenFactory.createJsonGenerator(mapper, out);
            // important to output utf-8 character instead of fucking '??????????'
            jsonGenerator = (CustomJsonGenerator) jsonGenerator.setHighestNonEscapedChar(127);
            if (entity != null) {
                AbstractEntityConverter converter =
                        (AbstractEntityConverter) ConverterFactory.getConverter(entity.getClass());
                converter.toJson(jsonGenerator, entity);
                jsonGenerator.flush();
            }
            return out.toString();
        }
    }
}
