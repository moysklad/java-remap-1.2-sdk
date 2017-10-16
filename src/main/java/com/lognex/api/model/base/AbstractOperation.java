package com.lognex.api.model.base;

import com.lognex.api.model.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class AbstractOperation extends AbstractEntityLegendable {
    private Date moment;
    private boolean applicable;
    private Double sum;
    private Contract contract;
    private Project project;
    private State state;
    private String syncId;
    private Currency rate;
    private Organization organization;
}
