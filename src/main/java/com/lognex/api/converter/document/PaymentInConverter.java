package com.lognex.api.converter.document;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.AbstractFinanceInConverter;
import com.lognex.api.converter.entity.AgentAccountConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.document.PaymentIn;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.util.MetaHrefParser;

public class PaymentInConverter extends AbstractFinanceInConverter<PaymentIn> {
    @Override
    protected PaymentIn convertFromJson(JsonNode node) throws ConverterException {
        PaymentIn result = new PaymentIn();
        super.convertToEntity(result, node);

        result.setAgentAccount(node.get("agentAccount").get("id") == null
                ? new AgentAccount(MetaHrefParser.getId(node.get("agentAccount").get("meta").get("href").asText()))
                : new AgentAccountConverter().convert(node.get("agentAccount").toString()));
        return result;
    }
}
