package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractEntityInfoable;
import com.lognex.api.util.DateUtils;

public abstract class AbstractEntityInfoableConverter<T extends AbstractEntityInfoable> extends AbstractEntityWithOwnerConverter<T> {
    protected void convertToEntity(final AbstractEntityInfoable entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setVersion(node.get("version").asLong());
        entity.setUpdated(DateUtils.parseDate(node.get("updated").asText()));
        entity.setCreated(node.get("created") == null ? null : DateUtils.parseDate(node.get("created").asText()));
        entity.setDeleted(node.get("deleted") == null ? null : DateUtils.parseDate(node.get("deleted").asText()));
    }
}
