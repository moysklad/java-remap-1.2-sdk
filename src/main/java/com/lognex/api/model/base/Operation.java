package com.lognex.api.model.base;

import com.lognex.api.model.entity.*;
import com.lognex.api.util.ID;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class Operation extends EntityLegendable {
    private Date moment;
    private boolean applicable;
    private Double sum;
    private Contract contract;
    private Project project;
    private State state;
    private ID syncId;
    private Currency rate;
    private Organization organization;
    private boolean vatEnabled;
    private boolean vatIncluded;
    private Agent agent;
}
