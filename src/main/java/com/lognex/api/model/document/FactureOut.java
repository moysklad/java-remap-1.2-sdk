package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractOperation;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class FactureOut extends AbstractOperation {

    @Getter
    private List<Demand> demands = new ArrayList<>();
}
