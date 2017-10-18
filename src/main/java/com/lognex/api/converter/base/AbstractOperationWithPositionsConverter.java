package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.base.AbstractOperationWithPositions;
import com.lognex.api.model.base.Position;
import com.lognex.api.model.base.field.CollectionMeta;
import com.lognex.api.model.base.field.EmbeddedCollectionRef;

import java.io.IOException;
import java.util.List;

public abstract class AbstractOperationWithPositionsConverter <T extends AbstractOperationWithPositions, P extends Position> extends AbstractOperationConverter<T> {
    private Class<P> positionType;

    AbstractOperationWithPositionsConverter(Class<P> positionType) {
        this.positionType = positionType;
    }

    @Override
    protected void convertToEntity(T entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setVatSum(ConverterUtil.getDouble(node, "vatSum"));
        // TODO не прокатит, т.к. даже с expand позиции к нам приходят как объект. Нужно глядеть в rows.
        entity.getPositions().addAll(ConverterUtil.getList(node, "positions", ConverterFactory.getConverter(positionType)));
        ObjectNode positionsMetaNode = (ObjectNode) node.get("positions").get("meta");
        String href = ConverterUtil.getString(positionsMetaNode, "href");
        String type = ConverterUtil.getString(positionsMetaNode, "type");
        int size = ConverterUtil.getInt(positionsMetaNode, "size");
        int limit = ConverterUtil.getInt(positionsMetaNode, "limit");
        int offset = ConverterUtil.getInt(positionsMetaNode, "offset");
        entity.setPositionsRef(new EmbeddedCollectionRef(new CollectionMeta(href, type, size, limit, offset)));
    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, T entity) throws IOException {
        super.convertFields(jgen, entity);
        if (entity.getPositions() != null && !entity.getPositions().isEmpty()){
            jgen.writeArrayFieldStart("positions");
            for (Position p : (List<Position>)entity.getPositions()){
                jgen.writeStartObject();
                convertMetaField(jgen, "assortment", (AbstractEntity) p.getAssortment());
                jgen.writeNumberField("quantity", p.getQuantity());
                jgen.writeNumberField("price", p.getPrice());
                jgen.writeNumberField("vat", p.getVat());
                jgen.writeNumberField("discount", p.getDiscount());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
        }
    }

}
