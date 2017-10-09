package com.lognex.api.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.converter.base.AbstractEntityConverter;
import com.lognex.api.converter.base.CustomJgenFactory;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.ApiClient;
import com.lognex.api.model.base.AbstractEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.lognex.api.util.Constants.APPLICATION_JSON;

@Slf4j
public class MSCreateRequest extends MSRequest {

    private List<? extends AbstractEntity> postEntityList;
    private final ObjectMapper mapper = new ObjectMapper();

    public MSCreateRequest(String url, ApiClient client, List<? extends AbstractEntity> entityList) {
        super(url, client);
        this.postEntityList = entityList;
    }

    @Override
    protected HttpUriRequest buildRequest() {
        HttpPost post = new HttpPost(getUrl());
        post.setHeader("ContentType", APPLICATION_JSON);
        StringEntity entity = null;
        try {
            entity = new StringEntity(convertToJsonBody());
            post.setEntity(entity);
            entity.setContentType(APPLICATION_JSON);
        } catch (UnsupportedEncodingException ignored) {
            log.error("Error while composing create request: ", ignored);
        } catch (IOException e) {
            log.error("Error while serializing entity to json", e);
        }

        return post;
    }

    private String convertToJsonBody() throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            CustomJsonGenerator jsonGenerator = CustomJgenFactory.createJsonGenerator(mapper, out);
            // important to output utf-8 character instead of fucking '??????????'
            jsonGenerator = (CustomJsonGenerator) jsonGenerator.setHighestNonEscapedChar(127);
            if (postEntityList.size() > 1){
                jsonGenerator.writeStartArray();
                for (AbstractEntity e: postEntityList){
                    AbstractEntityConverter converter =
                            (AbstractEntityConverter) ConverterFactory.getConverter(e.getClass());
                    converter.toJson(jsonGenerator, e);
                }
                jsonGenerator.writeEndArray();
                jsonGenerator.flush();
            } else if (!postEntityList.isEmpty()) {
                AbstractEntity e = postEntityList.get(0);
                AbstractEntityConverter converter =
                        (AbstractEntityConverter) ConverterFactory.getConverter(e.getClass());
                converter.toJson(jsonGenerator, e);
                jsonGenerator.flush();
            }
            return out.toString();
        }
    }

}
