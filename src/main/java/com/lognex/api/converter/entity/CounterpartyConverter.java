package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.AttributesConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Counterparty;

public class CounterpartyConverter extends AgentConverter<Counterparty> {

    private AttributesConverter attributesConverter = new AttributesConverter();

    @Override
    protected Counterparty convertFromJson(JsonNode node) throws ConverterException {
        Counterparty counterparty = new Counterparty();
        super.convertToEntity(counterparty, node);
        attributesConverter.fromJson(node, counterparty);
        return counterparty;
    }
}
