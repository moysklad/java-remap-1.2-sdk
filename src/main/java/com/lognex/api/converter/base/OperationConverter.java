package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.entity.*;
import com.lognex.api.converter.entity.CounterpartyConverter;
import com.lognex.api.converter.entity.OrganizationConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.Operation;
import com.lognex.api.util.DateUtils;

import java.io.IOException;
import com.lognex.api.model.entity.*;


public abstract class OperationConverter<T extends Operation> extends EntityLegendableConverter<T> {

    private static final CurrencyConverter currencyConverter = new CurrencyConverter();
    private static final OrganizationConverter organizationConverter = new OrganizationConverter();
    private static final CounterpartyConverter counterpartyConverter = new CounterpartyConverter();
    private static final ContractConverter contractConverter = new ContractConverter();
    private static final ProjectConverter projectConverter = new ProjectConverter();
    private static final StateConverter stateConverter = new StateConverter();

    @Override
    protected void convertToEntity(final T entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setMoment(ConverterUtil.getDate(node, "moment"));
        entity.setApplicable(ConverterUtil.getBoolean(node, "applicable"));
        entity.setSum(ConverterUtil.getDouble(node, "sum"));
        entity.setContract(ConverterUtil.getObject(node, "contract", contractConverter, new Contract()));
        entity.setProject(ConverterUtil.getObject(node, "project", projectConverter, new Project()));
        entity.setState(ConverterUtil.getObject(node, "state", stateConverter));
        entity.setSyncId(ConverterUtil.getId(node, "syncId"));
        entity.setRate(ConverterUtil.getObject(node, "rate", currencyConverter, new Currency()));
        entity.setOrganization(ConverterUtil.getObject(node, "organization", organizationConverter, new Organization()));
        entity.setVatEnabled(ConverterUtil.getBoolean(node, "vatEnabled"));
        entity.setVatIncluded(ConverterUtil.getBoolean(node, "vatIncluded"));
        entity.setAgent(ConverterUtil.getObject(node, "agent", counterpartyConverter, new Counterparty()));

    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, T entity) throws IOException {
        super.convertFields(jgen, entity);
        if (entity.getMoment() != null) {
            jgen.writeStringFieldIfNotEmpty("moment", DateUtils.format(entity.getMoment()));
        }
        jgen.writeBooleanField("applicable", entity.isApplicable());
        if (entity.getSum() != null) {
            jgen.writeNumberField("sum", entity.getSum());
        }
        if (entity.getSyncId() != null) {
            jgen.writeStringFieldIfNotEmpty("syncId", entity.getSyncId().getValue());
        }
        if (entity.getAgent() != null && entity.getAgent().getId() != null) {
            convertMetaField(jgen, "agent", entity.getAgent());
        }
        if (entity.getOrganization() != null) {
            convertMetaField(jgen, "organization", entity.getOrganization());
        }
        State state = entity.getState();
        if (state != null) {
            jgen.writeFieldName("state");
            stateConverter.toJson(jgen, state, host);
        }
    }
}
