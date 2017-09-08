package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.converter.Converter;
import com.lognex.api.converter.base.AbstractEntityInfoableConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.AgentAccount;

import java.io.IOException;
import java.util.List;

public class AgentAccountConverter extends AbstractEntityInfoableConverter implements Converter<AgentAccount> {
    @Override
    public AgentAccount convertToEntity(String response) throws ConverterException {
        try {
            JsonNode root = new ObjectMapper().readTree(response.getBytes());
            return convertToEntity(root);
        } catch (IOException e) {
            throw new ConverterException(e);
        }
    }

    @Override
    public List<AgentAccount> convertToListEntity(String response) throws ConverterException {
        return null;
    }

    private AgentAccount convertToEntity(JsonNode node) throws ConverterException {
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
