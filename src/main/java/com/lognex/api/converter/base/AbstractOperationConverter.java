package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.entity.CounterpartyConverter;
import com.lognex.api.converter.entity.OrganizationConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractOperation;
import com.lognex.api.model.entity.Agent;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.model.entity.Organization;
import com.lognex.api.util.DateUtils;
import com.lognex.api.util.MetaHrefUtils;

import java.io.IOException;

public abstract class AbstractOperationConverter<T extends AbstractOperation> extends AbstractEntityLegendableConverter<T> {
    protected void convertToEntity(final AbstractOperation entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setMoment(ConverterUtil.getDate(node, "moment"));
        entity.setApplicable(ConverterUtil.getBoolean(node, "applicable"));
        entity.setSum(ConverterUtil.getDouble(node, "sum"));
        entity.setSyncId(ConverterUtil.getString(node, "syncId"));

        if (node.get("agent") != null) {
            entity.setAgent(node.get("agent").get("id") == null
                    ? createAgentWithoutExpand(node.get("agent"))
                    : createAgentWithExpand(node.get("agent")));
        }
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
        jgen.writeStringFieldIfNotEmpty("syncId", entity.getSyncId());

        if (entity.getAgent() != null && entity.getAgent().getId() != null) {
            convertMetaField(jgen, "agent", entity.getAgent());
        }
    }


    private Agent createAgentWithoutExpand(JsonNode agent) {
        if (isCounterpaty(agent.get("meta"))) {
            return new Counterparty(MetaHrefUtils.getId(agent.get("meta").get("href").asText()));
        } else {
            return new Organization(MetaHrefUtils.getId(agent.get("meta").get("href").asText()));
        }
    }

    private Agent createAgentWithExpand(JsonNode agent) {
        if (isCounterpaty(agent.get("meta"))) {
            return new CounterpartyConverter().convert(agent.toString());
        } else {
            return new OrganizationConverter().convert(agent.toString());
        }
    }

    private boolean isCounterpaty(JsonNode meta) {
        return meta.get("type").asText().equals("counterparty");
    }
}
