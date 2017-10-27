package com.lognex.api.converter.content;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.converter.base.EntityConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.content.ExportTemplateSet;

import java.io.IOException;

public class ExportTemplateSetConverter extends EntityConverter<ExportTemplateSet> {
    @Override
    protected ExportTemplateSet convertFromJson(JsonNode node) throws ConverterException {
        return null;
    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, ExportTemplateSet entity) throws IOException {
        super.convertFields(jgen, entity);
        jgen.writeArrayFieldStart("templates");
        for (ExportTemplateSet.TemplateWithCount templateWithCount : entity.getTemplates()) {
            jgen.writeStartObject();
            convertMetaField(jgen, "template", templateWithCount.getTemplate());
            jgen.writeNumberField("count", templateWithCount.getCount());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
    }
}
