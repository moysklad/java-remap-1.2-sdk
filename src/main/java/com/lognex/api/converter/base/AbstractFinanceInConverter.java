package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractFinanceIn;

public abstract class AbstractFinanceInConverter extends AbstractFinanceConverer {
    protected void convertToEntity(final AbstractFinanceIn entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
    }
}
