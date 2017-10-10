package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractEntityLegendable;

import java.io.IOException;

public abstract class AbstractEntityLegendableConverter<T extends AbstractEntityLegendable> extends AbstractEntityInfoableConverter<T> {
    protected void convertToEntity(final T entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setName(node.get("name").asText());
        entity.setDescription(node.get("description") == null ? null : node.get("description").asText());
        entity.setCode(node.get("code") == null ? null : node.get("code").asText());
        entity.setExternalCode(node.get("externalCode").asText());
    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, T entity) throws IOException {
        super.convertFields(jgen, entity);
        jgen.writeStringFieldIfNotEmpty("name", entity.getName());
        jgen.writeStringFieldIfNotEmpty("description", entity.getDescription());
        jgen.writeStringFieldIfNotEmpty("code", entity.getCode());
        jgen.writeStringFieldIfNotEmpty("externalCode", entity.getExternalCode());
    }
}
