package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractFinance;
import com.lognex.api.util.DateParser;

public abstract class AbstractFinanceConverter<T extends AbstractFinance> extends AbstractOperationConverter<T> {
    protected void convertToEntity(final AbstractFinance entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setPaymentPurpose(node.get("paymentPurpose").asText());
        entity.setVatSum(node.get("vatSum").asDouble());
        entity.setIncomingNumber(node.get("incomingNumber").asText());
        entity.setIncomingDate(DateParser.parseDate(node.get("incomingDate").asText()));
    }
}
