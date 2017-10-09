package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.AbstractEntityLegendableConverter;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Agent;

import java.io.IOException;

public abstract class AgentConverter<T extends Agent> extends AbstractEntityLegendableConverter<T> {

    protected void convertToEntity(final Agent entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setLegalAddress(node.get("legalAddress") == null ? null : node.get("legalAddress").asText());
        entity.setInn(node.get("inn") == null ? null : node.get("inn").asText());
        entity.setKpp(node.get("kpp") == null ? null : node.get("kpp").asText());
    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, T entity) throws IOException {
        super.convertFields(jgen, entity);
        jgen.writeStringFieldIfNotEmpty("legalAddress", entity.getLegalAddress());
        jgen.writeStringFieldIfNotEmpty("inn", entity.getInn());
        jgen.writeStringFieldIfNotEmpty("kpp", entity.getKpp());
    }
}
