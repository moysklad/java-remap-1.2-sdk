package com.lognex.api.model.base;

import com.lognex.api.model.entity.Currency;
import com.lognex.api.model.entity.Organization;

import java.util.Date;

public abstract class AbstractOperation extends AbstractEntityLegendable {
    private Date moment;
    private boolean applicable;
    private Double sum;
    //private ID contract; /*TODO должны быть не ID, а entity*/
    //private ID project;
    //private ID state;
    private String syncId;
    private Currency rate;
    private Organization organization;

    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    public boolean isApplicable() {
        return applicable;
    }

    public void setApplicable(boolean applicable) {
        this.applicable = applicable;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getSyncId() {
        return syncId;
    }

    public void setSyncId(String syncId) {
        this.syncId = syncId;
    }

    public Currency getRate() {
        return rate;
    }

    public void setRate(Currency rate) {
        this.rate = rate;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
