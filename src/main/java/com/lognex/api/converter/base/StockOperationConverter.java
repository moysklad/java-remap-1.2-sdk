package com.lognex.api.converter.base;

import com.lognex.api.model.base.StockOperation;
import com.lognex.api.model.base.StockPosition;

abstract class StockOperationConverter <T extends StockOperation, P extends StockPosition> extends OperationWithPositionsConverter<T, P> {
    StockOperationConverter(Class<P> positionType) {
        super(positionType);
    }
}
