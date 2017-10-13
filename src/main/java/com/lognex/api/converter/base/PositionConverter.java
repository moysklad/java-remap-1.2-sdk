package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.model.base.Position;

public abstract class PositionConverter<P extends Position> extends AbstractEntityConverter<P> {
    protected Class<P> positionType;

    public PositionConverter(Class<P> positionType) {
        this.positionType = positionType;
    }

    protected void convertToEntity(final Position entity, JsonNode node) {
        entity.setQuantity(ConverterUtil.getInt(node, "quantity"));
        entity.setPrice(ConverterUtil.getLong(node, "price"));
        entity.setDiscount(ConverterUtil.getDouble(node, "discount"));
        entity.setVat(ConverterUtil.getLong(node, "vat"));
        entity.setAssortment(ConverterUtil.getObject(node, "assortment"));
    }

}
