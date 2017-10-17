package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractOperation;
import com.lognex.api.util.DateUtils;

public abstract class AbstractOperationConverter<T extends AbstractOperation> extends AbstractEntityLegendableConverter<T> {
    protected void convertToEntity(final AbstractOperation entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setMoment(ConverterUtil.getDate(node, "moment"));
        entity.setApplicable(ConverterUtil.getBoolean(node, "applicable"));
        entity.setSum(ConverterUtil.getDouble(node, "sum"));
        entity.setSyncId(ConverterUtil.getString(node, "syncId"));
    }
}
