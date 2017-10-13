package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractOperationWithPositions;
import com.lognex.api.model.base.Position;

public abstract class AbstractOperationWithPositionsConverter <T extends AbstractOperationWithPositions, P extends Position> extends AbstractOperationConverter<T> {
    protected Class<P> positionType;

    public AbstractOperationWithPositionsConverter(Class<P> positionType) {
        this.positionType = positionType;
    }

    @Override
    protected void convertToEntity(AbstractOperationWithPositions entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setVatSum(ConverterUtil.getDouble(node, "vatSum"));
        entity.getPositions().addAll(ConverterUtil.getList(node, "positions", ConverterFactory.getConverter(positionType)));
    }
}
