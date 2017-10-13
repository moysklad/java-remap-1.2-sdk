package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractFinance;
import com.lognex.api.util.DateUtils;

public abstract class AbstractFinanceConverter<T extends AbstractFinance> extends AbstractOperationConverter<T> {
    protected void convertToEntity(final AbstractFinance entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setPaymentPurpose(ConverterUtil.getString(node, "paymentPurpose"));
        entity.setVatSum(ConverterUtil.getDouble(node, "vatSum"));
        entity.setIncomingNumber(ConverterUtil.getString(node, "incomingNumber"));
        entity.setIncomingDate(ConverterUtil.getDate(node, "incomingDate"));
    }
}
