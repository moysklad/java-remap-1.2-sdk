package com.lognex.api.model.base;

import com.lognex.api.model.entity.Agent;
import com.lognex.api.model.entity.Currency;
import com.lognex.api.model.entity.Organization;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class AbstractOperation extends AbstractEntityLegendable {
    private Date moment;
    private boolean applicable;
    private Double sum;
    //private ID contract; /*TODO должны быть не ID, а entity*/
    //private ID project;
    //private ID state;
    private String syncId;
    private Currency rate;
    private Agent agent;
    private Organization organization;
}
