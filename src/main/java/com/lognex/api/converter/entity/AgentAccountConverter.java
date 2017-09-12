package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.AbstractEntityInfoableConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.AgentAccount;

public class AgentAccountConverter extends AbstractEntityInfoableConverter<AgentAccount> {

    @Override
    protected AgentAccount convertFromJson(JsonNode node) throws ConverterException {
        AgentAccount result = new AgentAccount();
        super.convertToEntity(result, node);

        result.setDefault(node.get("isDefault").asBoolean());
        result.setAccountNumber(node.get("accountNumber").asText());
        result.setBankName(node.get("bankName").asText());
        result.setBankLocation(node.get("bankLocation").asText());
        result.setCorrespondentAccount(node.get("correspondentAccount").asText());
        result.setBic(node.get("bic").asText());

        return result;
    }
}
