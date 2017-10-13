package com.lognex.api.model.base;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public abstract class AbstractOperationWithPositions <P extends Position> extends AbstractOperation implements IOperationWithPositions<P> {

    protected List<P> positions = new ArrayList<>();
    private double vatSum;

}
