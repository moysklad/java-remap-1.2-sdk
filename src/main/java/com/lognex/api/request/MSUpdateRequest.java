package com.lognex.api.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.ApiClient;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.converter.base.CustomJgenFactory;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.converter.base.EntityConverter;
import com.lognex.api.model.base.Entity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpPut;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MSUpdateRequest<T extends Entity> extends MSRequestWithBody {

    private T putEntity;
    private final ObjectMapper mapper = new ObjectMapper();

    public MSUpdateRequest(String url, ApiClient client, T entity) {
        super(url, client);
        this.putEntity = entity;
    }

    @Override
    protected HttpEntityEnclosingRequest produceHttpUriRequest(String url) {
        return new HttpPut(url);
    }

    @Override
    protected String convertToJsonBody() throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            CustomJsonGenerator jsonGenerator = CustomJgenFactory.createJsonGenerator(mapper, out);
            // important to output utf-8
            jsonGenerator = (CustomJsonGenerator) jsonGenerator.setHighestNonEscapedChar(127);
            if (putEntity != null) {
                EntityConverter converter =
                        (EntityConverter) ConverterFactory.getConverter(putEntity.getClass());
                converter.toJson(jsonGenerator, putEntity, getClient().getHost());
                jsonGenerator.flush();
            }
            return out.toString();
        }
    }
}
