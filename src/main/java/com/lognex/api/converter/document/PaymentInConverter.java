package com.lognex.api.converter.document;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.AbstractFinanceInConverter;
import com.lognex.api.converter.entity.AgentAccountConverter;
import com.lognex.api.converter.entity.CounterpartyConverter;
import com.lognex.api.converter.entity.OrganizationConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.document.PaymentIn;
import com.lognex.api.model.entity.Agent;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.model.entity.Organization;
import com.lognex.api.util.MetaHrefUtils;

public class PaymentInConverter extends AbstractFinanceInConverter<PaymentIn> {
    @Override
    protected PaymentIn convertFromJson(JsonNode node) throws ConverterException {
        PaymentIn result = new PaymentIn();
        super.convertToEntity(result, node);

        if (node.get("agentAccount") != null) {
            result.setAgentAccount(node.get("agentAccount").get("id") == null
                    ? new AgentAccount(MetaHrefUtils.getId(node.get("agentAccount").get("meta").get("href").asText()))
                    : new AgentAccountConverter().convert(node.get("agentAccount").toString()));
        }

        return result;
    }
}
