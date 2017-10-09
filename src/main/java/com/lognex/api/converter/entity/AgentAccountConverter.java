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

        result.setDefault(node.has("isDefault") && node.get("isDefault").asBoolean());
        result.setAccountNumber(node.has("accountNumber") ? node.get("accountNumber").asText() : null);
        result.setBankName(node.has("bankName") ? node.get("bankName").asText() : null);
        result.setBankLocation(node.has("bankLocation")? node.get("bankLocation").asText() : null);
        result.setCorrespondentAccount(node.has("correspondentAccount")? node.get("correspondentAccount").asText() : null);
        result.setBic(node.has("bic")? node.get("bic").asText() : null);

        return result;
    }
}
