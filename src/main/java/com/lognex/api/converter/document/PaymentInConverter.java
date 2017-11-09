package com.lognex.api.converter.document;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.converter.base.FinanceInConverter;
import com.lognex.api.converter.entity.AgentAccountConverter;
import com.lognex.api.converter.entity.StateConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.document.PaymentIn;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.State;
import com.lognex.api.util.MetaHrefUtils;

import java.io.IOException;

public class PaymentInConverter extends FinanceInConverter<PaymentIn> {

    private StateConverter stateConverter = new StateConverter();

    @Override
    protected PaymentIn convertFromJson(JsonNode node) throws ConverterException {
        PaymentIn result = new PaymentIn();
        super.convertToEntity(result, node);

        if (node.get("agentAccount") != null) {
            result.setAgentAccount(node.get("agentAccount").get("id") == null
                    ? new AgentAccount(MetaHrefUtils.getId(node.get("agentAccount").get("meta").get("href").asText()))
                    : new AgentAccountConverter().convert(node.get("agentAccount").toString()));
        }
        result.setState(ConverterUtil.getObject(node, "state", stateConverter));
        return result;
    }

    @Override
    protected void convertToEntity(final PaymentIn entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setIncomingNumber(ConverterUtil.getString(node, "incomingNumber"));
        entity.setIncomingDate(ConverterUtil.getDate(node, "incomingDate"));
    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, PaymentIn entity) throws IOException {
        super.convertFields(jgen, entity);

        State state = entity.getState();
        if (state != null) {
            jgen.writeFieldName("state");
            stateConverter.toJson(jgen, state, host);
        }
    }
}
