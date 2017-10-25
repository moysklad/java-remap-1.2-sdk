package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.base.EntityLegendableConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Currency;

public class CurrencyConverter extends EntityLegendableConverter<Currency> {

    @Override
    protected Currency convertFromJson(JsonNode node) throws ConverterException {
        Currency entity = new Currency();
        convertToEntity(entity, node);
        return entity;
    }

    @Override
    protected void convertToEntity(Currency entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setFullName(ConverterUtil.getString(node, "fullName"));
        entity.setCode(ConverterUtil.getString(node, "code"));
        entity.setIsoCode(ConverterUtil.getString(node, "isoCode"));
    }
}
