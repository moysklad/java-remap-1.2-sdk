package com.lognex.api.converter.document;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.AbstractOperationConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractOperation;

public class FactureOutConverter extends AbstractOperationConverter<AbstractOperation> {

    @Override
    protected AbstractOperation convertFromJson(JsonNode node) throws ConverterException {
        return null;
    }
}
