package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractEntityInfoable;

public abstract class AbstractEntityInfoableConverter<T extends AbstractEntityInfoable> extends AbstractEntityWithOwnerConverter<T> {
    @Override
    protected void convertToEntity(final T entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setVersion(ConverterUtil.getLong(node, "version"));
        entity.setUpdated(ConverterUtil.getDate(node, "updated"));
        entity.setCreated(ConverterUtil.getDate(node, "created"));
        entity.setDeleted(ConverterUtil.getDate(node,"deleted"));
    }
}
