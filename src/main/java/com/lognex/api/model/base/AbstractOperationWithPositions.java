package com.lognex.api.model.base;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AbstractOperationWithPositions <P extends Position> extends AbstractOperation {

    protected List<P> positions = new ArrayList<>();
    private double vatSum;

}
