package com.lognex.api.converter.content;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.converter.base.EntityConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.content.ExportTemplate;

import java.io.IOException;

public class ExportTemplateConverter extends EntityConverter<ExportTemplate> {
    @Override
    protected ExportTemplate convertFromJson(JsonNode node) throws ConverterException {
        return null;
    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, ExportTemplate entity) throws IOException {
        if (entity != null) {
            convertMetaField(jgen, "template", entity.getTemplate());
            jgen.writeStringField("extension", entity.getExtension().toString());
        }
    }
}
