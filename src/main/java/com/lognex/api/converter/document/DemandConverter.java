package com.lognex.api.converter.document;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.base.ComingOutOperationConverter;
import com.lognex.api.converter.entity.AgentAccountConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.ShipmentOutPosition;
import com.lognex.api.model.document.Demand;

public class DemandConverter extends ComingOutOperationConverter<Demand, ShipmentOutPosition> {
    private AgentAccountConverter agentAccountConverter = new AgentAccountConverter();

    public DemandConverter() {
        super(ShipmentOutPosition.class);
    }

    @Override
    protected Demand convertFromJson(JsonNode node) throws ConverterException {
        Demand operation = new Demand();
        convertToEntity(operation, node);
        return operation;
    }

    @Override
    protected void convertToEntity(Demand entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setOrganizationAccount(ConverterUtil.getObject(node, "organizationAccount", agentAccountConverter));
        entity.setAgentAccount(ConverterUtil.getObject(node, "agentAccount", agentAccountConverter));
    }


}
