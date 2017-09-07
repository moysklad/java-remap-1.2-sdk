package com.lognex.api.model.base;

import com.lognex.api.model.entity.Currency;
import com.lognex.api.model.entity.Organization;
import com.lognex.api.util.ID;

import java.util.Date;

public abstract class AbstractOperation extends AbstractEntityLegendable {
    private Date moment;
    private boolean applicable;
    private Double sum;
    private ID contract;
    private ID project;
    private ID state;
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

    public ID getContract() {
        return contract;
    }

    public void setContract(ID contract) {
        this.contract = contract;
    }

    public ID getProject() {
        return project;
    }

    public void setProject(ID project) {
        this.project = project;
    }

    public ID getState() {
        return state;
    }

    public void setState(ID state) {
        this.state = state;
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
