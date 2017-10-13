package com.lognex.api.model.base;

import java.util.List;

public interface IOperationWithPositions<P extends Position> {
    List<P> getPositions();
}
