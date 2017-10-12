package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtill;
import com.lognex.api.converter.base.AbstractEntityLegendableConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Currency;

public class CurrencyConverter extends AbstractEntityLegendableConverter<Currency> {

    @Override
    protected Currency convertFromJson(JsonNode node) throws ConverterException {
        Currency entity = new Currency();
        convertToEntity(entity, node);
        return entity;
    }

    @Override
    protected void convertToEntity(Currency entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setFullName(ConverterUtill.getString(node, "fullName"));
        entity.setCode(ConverterUtill.getString(node, "code"));
        entity.setIsoCode(ConverterUtill.getString(node, "isoCode"));
    }
}
