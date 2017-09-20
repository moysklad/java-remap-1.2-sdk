package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Organization;

public class OrganizationConverter extends AgentConverter<Organization> {
    @Override
    protected Organization convertFromJson(JsonNode node) throws ConverterException {
        Organization organization = new Organization();
        super.convertToEntity(organization, node);
        return organization;
    }
}
