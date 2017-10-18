package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractFinanceIn;

public abstract class AbstractFinanceInConverter<T extends AbstractFinanceIn> extends AbstractFinanceConverter<T> {
    @Override
    protected void convertToEntity(final T entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
    }
}
