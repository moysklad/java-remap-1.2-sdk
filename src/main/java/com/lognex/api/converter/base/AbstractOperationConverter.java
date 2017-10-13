package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.entity.*;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractOperation;

public abstract class AbstractOperationConverter<T extends AbstractOperation> extends AbstractEntityLegendableConverter<T> {

    private CurrencyConverter currencyConverter = new CurrencyConverter();
    private OrganizationConverter organizationConverter = new OrganizationConverter();
    private CounterpartyConverter counterpartyConverter = new CounterpartyConverter();
    private ContractConverter contractConverter = new ContractConverter();
    private ProjectConverter projectConverter = new ProjectConverter();

    protected void convertToEntity(final AbstractOperation entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setMoment(ConverterUtil.getDate(node, "moment"));
        entity.setApplicable(ConverterUtil.getBoolean(node, "applicable"));
        entity.setSum(ConverterUtil.getDouble(node, "sum"));
        entity.setContract(ConverterUtil.getObject(node, "contract", contractConverter));
        entity.setProject(ConverterUtil.getObject(node, "project", projectConverter));
        entity.setSyncId(ConverterUtil.getId(node, "syncId"));
        entity.setRate(ConverterUtil.getObject(node, "rate", currencyConverter));
        entity.setOrganization(ConverterUtil.getObject(node, "rate", organizationConverter));
        entity.setVatEnabled(ConverterUtil.getBoolean(node, "vatEnabled"));
        entity.setVatIncluded(ConverterUtil.getBoolean(node, "vatIncluded"));
        entity.setAgent(ConverterUtil.getObject(node, "agent", counterpartyConverter));
    }
}
