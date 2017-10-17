package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.AbstractEntityConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Contract;

public class ContractConverter extends AbstractEntityConverter<Contract> {
    @Override
    protected Contract convertFromJson(JsonNode node) throws ConverterException {
        return null; //TODO do whatever you want but replace null with Contract
    }
}
