package com.lognex.api.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.converter.base.EntityConverter;
import com.lognex.api.converter.base.CustomJgenFactory;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.ApiClient;
import com.lognex.api.model.base.Entity;
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

    private List<? extends Entity> postEntityList;
    private ObjectMapper mapper = new ObjectMapper();

    public MSCreateRequest(String url, ApiClient client, List<? extends Entity> entityList) {
        super(url, client);
        this.postEntityList = entityList;
        mapper = mapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
    }


    @Override
    protected HttpEntityEnclosingRequest produceHttpUriRequest(String url) {
        return new HttpPost(url);
    }

    @Override
    protected String convertToJsonBody() throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            CustomJsonGenerator jsonGenerator = CustomJgenFactory.createJsonGenerator(mapper, out);
            // important to output utf-8
            jsonGenerator = (CustomJsonGenerator) jsonGenerator.setHighestNonEscapedChar(127);
            if (postEntityList.size() > 1){
                jsonGenerator.writeStartArray();
                for (Entity e: postEntityList){
                    EntityConverter converter =
                            (EntityConverter) ConverterFactory.getConverter(e.getClass());
                    converter.toJson(jsonGenerator, e, getClient().getHost());
                }
                jsonGenerator.writeEndArray();
                jsonGenerator.flush();
            } else if (!postEntityList.isEmpty()) {
                Entity e = postEntityList.get(0);
                EntityConverter converter =
                        (EntityConverter) ConverterFactory.getConverter(e.getClass());
                converter.toJson(jsonGenerator, e, getClient().getHost());
                jsonGenerator.flush();
            }
            return out.toString();
        }
    }

}
