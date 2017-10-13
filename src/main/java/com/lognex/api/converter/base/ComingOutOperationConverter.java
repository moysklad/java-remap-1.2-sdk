package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterFactory;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.entity.StoreConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.ComingOutOperation;
import com.lognex.api.model.base.ComingOutPosition;
import com.lognex.api.model.entity.Store;

public abstract class ComingOutOperationConverter <T extends ComingOutOperation, P extends ComingOutPosition> extends StockOperationConverter<T, P> {

    public ComingOutOperationConverter(Class<P> positionType) {
        super(positionType);
    }

    @Override
    protected void convertToEntity(ComingOutOperation entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setStore(ConverterUtil.getObject(node, "store", (Converter<Store>) ConverterFactory.getConverter(Store.class)));
    }
}
