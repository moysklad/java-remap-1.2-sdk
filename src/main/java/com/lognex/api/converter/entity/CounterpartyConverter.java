package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.base.AttributesConverter;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.converter.field.CompanyType;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.util.DateUtils;

import java.io.IOException;

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
        entity.setCompanyType(node.get("companyType") == null? null : CompanyType.valueOf(node.get("companyType").asText().toUpperCase()));
        entity.setLegalTitle(node.get("legalTitle") == null ? null : node.get("legalTitle").asText());
        entity.setOgrn(node.get("ogrn") == null ? null : node.get("ogrn").asText());
        entity.setOgrnip(node.get("ogrnip") == null ? null : node.get("ogrnip").asText());
        entity.setOkpo(node.get("okpo") == null? null : node.get("okpo").asText());
        entity.setCertificateNumber(node.get("certificateNumber") == null? null : node.get("certificateNumber").asText());
        entity.setCertificateDate(node.get("certificateDate") == null? null : DateUtils.parseDate(node.get("certificateDate").asText()));
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
    }
}
