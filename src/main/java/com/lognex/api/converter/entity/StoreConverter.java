package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.EntityConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Store;

public class StoreConverter extends EntityConverter<Store> {
    @Override
    protected Store convertFromJson(JsonNode node) throws ConverterException {
        Store store = new Store();
        convertToEntity(store, node);
        return store;
    }
}
