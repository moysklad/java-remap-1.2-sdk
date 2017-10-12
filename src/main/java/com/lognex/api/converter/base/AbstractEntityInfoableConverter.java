package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtill;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractEntityInfoable;

public abstract class AbstractEntityInfoableConverter<T extends AbstractEntityInfoable> extends AbstractEntityWithOwnerConverter<T> {
    protected void convertToEntity(final AbstractEntityInfoable entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setVersion(ConverterUtill.getLong(node, "version"));
        entity.setUpdated(ConverterUtill.getDate(node,"updated"));
        entity.setCreated(ConverterUtill.getDate(node,"created"));
        entity.setDeleted(ConverterUtill.getDate(node,"deleted"));
    }
}
