package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.AbstractEntityConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Project;

public class ProjectConverter extends AbstractEntityConverter<Project> {
    @Override
    protected Project convertFromJson(JsonNode node) throws ConverterException {
        return null;
    }
}
