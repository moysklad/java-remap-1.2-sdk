package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.AbstractEntityLegendableConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Agent;

public abstract class AgentConverter<T extends Agent> extends AbstractEntityLegendableConverter<T> {

    protected void convertToEntity(final Agent entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setLegalAddress(node.get("legalAddress") == null ? null : node.get("legalAddress").asText());
        entity.setInn(node.get("inn") == null ? null : node.get("inn").asText());
        entity.setKpp(node.get("kpp") == null ? null : node.get("kpp").asText());
    }
}
