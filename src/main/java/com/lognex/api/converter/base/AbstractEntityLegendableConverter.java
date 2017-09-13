package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractEntityLegendable;

public abstract class AbstractEntityLegendableConverter<T extends AbstractEntityLegendable> extends AbstractEntityInfoableConverter<T> {
    protected void convertToEntity(final AbstractEntityLegendable entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setName(node.get("name").asText());
        entity.setDescription(node.get("description") == null ? null : node.get("description").asText());
        entity.setCode(node.get("code") == null ? null : node.get("code").asText());
        entity.setExternalCode(node.get("externalCode").asText());
    }
}
