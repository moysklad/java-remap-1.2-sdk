package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.AbstractEntityConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Store;

public class StoreConverter extends AbstractEntityConverter<Store> {
    @Override
    protected Store convertFromJson(JsonNode node) throws ConverterException {
        return null;
    }
}
