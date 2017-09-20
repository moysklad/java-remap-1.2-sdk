package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Counterparty;

public class CounterpartyConverter extends AgentConverter<Counterparty> {
    @Override
    protected Counterparty convertFromJson(JsonNode node) throws ConverterException {
        Counterparty counterparty = new Counterparty();
        super.convertToEntity(counterparty, node);
        return counterparty;
    }
}
