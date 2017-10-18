package com.lognex.api.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.ApiClient;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.converter.base.AbstractEntityConverter;
import com.lognex.api.converter.base.CustomJgenFactory;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.model.base.AbstractEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpPut;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class MSTemplateRequest extends MSRequestWithBody {


    private ObjectMapper mapper = new ObjectMapper();
    private final AbstractEntity entity;

    public <T extends AbstractEntity> MSTemplateRequest(String url, ApiClient client, T entity) {
        super(url, client);
        this.entity = entity;
    }

    @Override
    protected HttpEntityEnclosingRequest produceHttpUriRequest() {
        return new HttpPut(getUrl());
    }

    @Override
    protected String convertToJsonBody() throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            CustomJsonGenerator jsonGenerator = CustomJgenFactory.createJsonGenerator(mapper, out);
            // important to output utf-8 character instead of '??????????'
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
