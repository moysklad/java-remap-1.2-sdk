package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.base.EntityConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Price;

public class PriceConverter extends EntityConverter<Price> {

    private CurrencyConverter currencyConverter = new CurrencyConverter();

    @Override
    protected Price convertFromJson(JsonNode node) throws ConverterException {
        Price entity = new Price();
        entity.setValue(ConverterUtil.getDouble(node, "value"));
        entity.setCurrency(currencyConverter.convertFromJson(node.get("currency")));
        entity.setPriceType(ConverterUtil.getString(node, "priceType"));
        return entity;
    }

}
