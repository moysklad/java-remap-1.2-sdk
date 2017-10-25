package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.base.EntityInfoableConverter;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.AgentAccount;

import java.io.IOException;

public class AgentAccountConverter extends EntityInfoableConverter<AgentAccount> {

    @Override
    protected AgentAccount convertFromJson(JsonNode node) throws ConverterException {
        AgentAccount result = new AgentAccount();
        super.convertToEntity(result, node);

        result.setDefault(ConverterUtil.getBoolean(node,"isDefault"));
        result.setAccountNumber(ConverterUtil.getString(node,"accountNumber"));
        result.setBankName(ConverterUtil.getString(node,"bankName"));
        result.setBankLocation(ConverterUtil.getString(node,"bankLocation"));
        result.setCorrespondentAccount(ConverterUtil.getString(node,"correspondentAccount"));
        result.setBic(ConverterUtil.getString(node,"bic"));

        return result;
    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, AgentAccount entity) throws IOException {
        super.convertFields(jgen, entity);
        jgen.writeBooleanField("isDefault", entity.isDefault());
        jgen.writeStringFieldIfNotEmpty("accountNumber", entity.getAccountNumber());
        jgen.writeStringFieldIfNotEmpty("bankName", entity.getBankName());
        jgen.writeStringFieldIfNotEmpty("bankLocation", entity.getBankLocation());
        jgen.writeStringFieldIfNotEmpty("correspondentAccount", entity.getCorrespondentAccount());
        jgen.writeStringFieldIfNotEmpty("bic", entity.getBic());
    }
}
