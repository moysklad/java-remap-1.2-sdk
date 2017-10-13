package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.base.AbstractEntityLegendableConverter;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Agent;

import java.io.IOException;

public abstract class AgentConverter<T extends Agent> extends AbstractEntityLegendableConverter<T> {

    @Override
    protected void convertToEntity(final T entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setLegalAddress(ConverterUtil.getString(node,"legalAddress"));
        entity.setInn(ConverterUtil.getString(node, "inn"));
        entity.setKpp(ConverterUtil.getString(node, "kpp"));

    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, T entity) throws IOException {
        super.convertFields(jgen, entity);
        jgen.writeStringFieldIfNotEmpty("legalAddress", entity.getLegalAddress());
        jgen.writeStringFieldIfNotEmpty("inn", entity.getInn());
        jgen.writeStringFieldIfNotEmpty("kpp", entity.getKpp());
    }
}
