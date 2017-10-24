package com.lognex.api.converter.document;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lognex.api.converter.base.AbstractOperationConverter;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.field.Meta;
import com.lognex.api.model.document.Demand;
import com.lognex.api.model.document.FactureOut;
import com.lognex.api.util.ID;
import com.lognex.api.util.MetaHrefUtils;
import com.lognex.api.util.Type;

import java.io.IOException;

public class FactureOutConverter extends AbstractOperationConverter<FactureOut> {

    @Override
    protected FactureOut convertFromJson(JsonNode node) throws ConverterException {
        FactureOut factureOut = new FactureOut();
        super.convertToEntity(factureOut, node);

        if (node.has("demands")){
            ArrayNode demandsNode = (ArrayNode) node.get("demands");
            for (int i = 0; i < demandsNode.size(); ++i){
                ObjectNode demandNode = (ObjectNode) demandsNode.get(i);
                ObjectNode metaNode = (ObjectNode) demandNode.get("meta");
                ID id = MetaHrefUtils.getId(metaNode.get("href").asText());
                Demand demand = new Demand();
                demand.setId(id);
                factureOut.getDemands().add(demand);
            }
        }
        return factureOut;
    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, FactureOut entity) throws IOException {
        super.convertFields(jgen, entity);
        if (!entity.getDemands().isEmpty()){
            jgen.writeArrayFieldStart("demands");
            for (Demand d : entity.getDemands()){
                if (d.getId() != null){
                    jgen.writeStartObject();
                    jgen.writeObjectField("meta", new Meta<>(Type.DEMAND, d, host));
                    jgen.writeEndObject();
                }
            }
            jgen.writeEndArray();
        }
    }
}
