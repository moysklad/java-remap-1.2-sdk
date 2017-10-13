package com.lognex.api.converter.base;

import com.lognex.api.model.base.StockOperation;
import com.lognex.api.model.base.StockPosition;

public abstract class StockOperationConverter <T extends StockOperation, P extends StockPosition> extends AbstractOperationWithPositionsConverter<T, P> {
    public StockOperationConverter(Class<P> positionType) {
        super(positionType);
    }
}
