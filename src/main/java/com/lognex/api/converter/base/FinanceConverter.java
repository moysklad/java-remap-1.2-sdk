package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.Finance;

public abstract class FinanceConverter<T extends Finance> extends OperationConverter<T> {
    @Override
    protected void convertToEntity(final T entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setPaymentPurpose(ConverterUtil.getString(node, "paymentPurpose"));
        entity.setVatSum(ConverterUtil.getDouble(node, "vatSum"));
    }
}
