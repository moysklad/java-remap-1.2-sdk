package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.TemplateConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.EmbeddedTemplate;

public class EmbeddedTemplateConverter extends TemplateConverter<EmbeddedTemplate> {
    @Override
    protected EmbeddedTemplate convertFromJson(JsonNode node) throws ConverterException {
        EmbeddedTemplate template = new EmbeddedTemplate();
        convertToEntity(template, node);
        return template;
    }
}
