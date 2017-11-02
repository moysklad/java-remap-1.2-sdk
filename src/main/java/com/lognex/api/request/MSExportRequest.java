package com.lognex.api.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.ApiClient;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.converter.base.Converter;
import com.lognex.api.converter.base.CustomJgenFactory;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.model.base.Entity;
import com.lognex.api.model.content.ExportTemplate;
import com.lognex.api.model.content.ExportTemplateSet;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpPost;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MSExportRequest extends MSRequestWithBody {
    private ObjectMapper mapper = new ObjectMapper();
    private ExportTemplateSet templateSet;
    private ExportTemplate template;

    public MSExportRequest(String url, ApiClient client, ExportTemplate template) {
        super(url, client);
        this.template = template;
    }

    public MSExportRequest(String url, ApiClient client, ExportTemplateSet templateSet) {
        super(url, client);
        this.templateSet = templateSet;
    }

    @Override
    protected HttpEntityEnclosingRequest produceHttpUriRequest(String url) {
        return new HttpPost(url);
    }

    @Override
    protected String convertToJsonBody() throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            CustomJsonGenerator jgen = CustomJgenFactory.createJsonGenerator(mapper, out);
            jgen = (CustomJsonGenerator) jgen.setHighestNonEscapedChar(127);

            if (template != null) {
                convertTemplateToJson(jgen, template, ExportTemplate.class);
            } else if (templateSet != null) {
                convertTemplateToJson(jgen, templateSet, ExportTemplateSet.class);
            }
            jgen.flush();

            return out.toString();
        }
    }

    private <T extends Entity> void convertTemplateToJson(CustomJsonGenerator jsonGenerator, T template, Class<T> temlateClass) throws IOException {
        Converter<T> converter = (Converter<T>) ConverterFactory.getConverter(temlateClass);
        converter.toJson(jsonGenerator, template, getClient().getHost());
    }
}
