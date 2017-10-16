package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.base.AttributesConverter;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.model.base.field.CompanyType;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.Counterparty;

import java.io.IOException;
import java.util.Collection;

public class CounterpartyConverter extends AgentConverter<Counterparty> {

    private AttributesConverter attributesConverter = new AttributesConverter();

    @Override
    protected Counterparty convertFromJson(JsonNode node) throws ConverterException {
        Counterparty counterparty = new Counterparty();
        convertToEntity(counterparty, node);
        attributesConverter.fromJson(node, counterparty);
        return counterparty;
    }

    @Override
    protected void convertToEntity(Counterparty entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setCompanyType(ConverterUtil.getString(node, "companyType") == null? null :
                CompanyType.valueOf(ConverterUtil.getString(node, "companyType").toUpperCase()));
        entity.setLegalTitle(ConverterUtil.getString(node, "legalTitle"));
        entity.setOgrn(ConverterUtil.getString(node, "ogrn"));
        entity.setOgrnip(ConverterUtil.getString(node, "ogrnip"));
        entity.setOkpo(ConverterUtil.getString(node, "okpo"));
        entity.setCertificateNumber(ConverterUtil.getString(node, "certificateNumber"));
        entity.setCertificateDate(ConverterUtil.getDate(node, "certificateDate"));
        entity.setEmail(ConverterUtil.getString(node, "email"));
        entity.getAccounts().clear();
        entity.getAccounts().addAll((Collection<? extends AgentAccount>) ConverterUtil.getListFromExpand(node, "accounts", ConverterFactory.getConverter(AgentAccount.class)));
    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, Counterparty entity) throws IOException {
        super.convertFields(jgen, entity);
        jgen.writeStringFieldIfNotEmpty("legalTitle", entity.getLegalTitle());
        if (entity.getCompanyType() != null) {
            jgen.writeStringFieldIfNotEmpty("companyType", entity.getCompanyType().name().toLowerCase());

            switch (entity.getCompanyType()){
                case LEGAL: {
                    jgen.writeStringFieldIfNotEmpty("kpp", entity.getKpp());
                    jgen.writeStringFieldIfNotEmpty("ogrn", entity.getOgrn());
                    jgen.writeStringFieldIfNotEmpty("okpo", entity.getOkpo());
                    break;
                }
                case ENTREPRENEUR: {
                    jgen.writeStringFieldIfNotEmpty("okpo", entity.getOkpo());
                    jgen.writeStringFieldIfNotEmpty("ogrnip", entity.getOgrnip());
                    jgen.writeStringFieldIfNotEmpty("certificateNumber", entity.getCertificateNumber());
                    if (entity.getCertificateDate() != null) {
                        jgen.writeObjectField("certificateDate", entity.getCertificateDate());
                    }
                    break;
                }
                case INDIVIDUAL:
                    break;
                default:
                    throw new IllegalStateException("No such companyType=" + entity.getCompanyType().name());
            }
        }
        attributesConverter.toJson(jgen, entity);
        jgen.writeStringFieldIfNotEmpty("email", entity.getEmail());
    }
}
