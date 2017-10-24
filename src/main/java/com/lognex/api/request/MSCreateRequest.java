package com.lognex.api.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.converter.base.AbstractEntityConverter;
import com.lognex.api.converter.base.CustomJgenFactory;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.ApiClient;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpPost;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
public class MSCreateRequest extends MSRequestWithBody {

    private List<? extends AbstractEntity> postEntityList;
    private ObjectMapper mapper = new ObjectMapper();

    public MSCreateRequest(String url, ApiClient client, List<? extends AbstractEntity> entityList) {
        super(url, client);
        this.postEntityList = entityList;
        mapper = mapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
    }


    @Override
    protected HttpEntityEnclosingRequest produceHttpUriRequest() {
        return new HttpPost(getUrl());
    }

    @Override
    protected String convertToJsonBody() throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            CustomJsonGenerator jsonGenerator = CustomJgenFactory.createJsonGenerator(mapper, out);
            // important to output utf-8
            jsonGenerator = (CustomJsonGenerator) jsonGenerator.setHighestNonEscapedChar(127);
            if (postEntityList.size() > 1){
                jsonGenerator.writeStartArray();
                for (AbstractEntity e: postEntityList){
                    AbstractEntityConverter converter =
                            (AbstractEntityConverter) ConverterFactory.getConverter(e.getClass());
                    converter.toJson(jsonGenerator, e, getClient().getHost());
                }
                jsonGenerator.writeEndArray();
                jsonGenerator.flush();
            } else if (!postEntityList.isEmpty()) {
                AbstractEntity e = postEntityList.get(0);
                AbstractEntityConverter converter =
                        (AbstractEntityConverter) ConverterFactory.getConverter(e.getClass());
                converter.toJson(jsonGenerator, e, getClient().getHost());
                jsonGenerator.flush();
            }
            return out.toString();
        }
    }

}
