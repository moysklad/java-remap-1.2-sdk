package com.lognex.api.model.base;

import com.lognex.api.model.entity.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ComingOutOperation <T extends ComingOutPosition> extends StockOperation<T> {
    private Store store;
}
