package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.TemplateConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.CustomTemplate;

public class CustomTemplateConverter extends TemplateConverter<CustomTemplate> {
    @Override
    protected CustomTemplate convertFromJson(JsonNode node) throws ConverterException {
        CustomTemplate template = new CustomTemplate();
        convertToEntity(template, node);
        return template;
    }
}
