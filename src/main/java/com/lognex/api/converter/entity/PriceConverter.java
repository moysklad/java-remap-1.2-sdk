package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtill;
import com.lognex.api.converter.base.AbstractEntityConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Price;

public class PriceConverter extends AbstractEntityConverter<Price> {

    private CurrencyConverter currencyConverter = new CurrencyConverter();

    @Override
    protected Price convertFromJson(JsonNode node) throws ConverterException {
        Price entity = new Price();
        entity.setValue(ConverterUtill.getDouble(node, "value"));
        entity.setCurrency(currencyConverter.convertFromJson(node.get("currency")));
        entity.setPriceType(ConverterUtill.getString(node, "priceType"));
        return entity;
    }

}
