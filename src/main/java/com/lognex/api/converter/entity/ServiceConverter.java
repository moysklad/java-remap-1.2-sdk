package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.AttributesConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.good.Service;

public class ServiceConverter extends BaseProductConverter<Service> {
    private AttributesConverter attributesConverter = new AttributesConverter();

    @Override
    protected void convertToEntity(Service entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
    }

    @Override
    protected Service convertFromJson(JsonNode node) throws ConverterException {
        Service entity = new Service();
        convertToEntity(entity, node);
        attributesConverter.fromJson(node, entity);
        return entity;
    }
}
