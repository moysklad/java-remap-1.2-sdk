package com.lognex.api.model.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractAgent extends AbstractEntityLegendable {
    private boolean archived;
}
