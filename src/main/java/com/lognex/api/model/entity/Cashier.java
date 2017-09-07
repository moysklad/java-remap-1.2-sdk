package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntity;

public class Cashier extends AbstractEntity {
    private Employee employee;
    private RetailStore retailStore;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public RetailStore getRetailStore() {
        return retailStore;
    }

    public void setRetailStore(RetailStore retailStore) {
        this.retailStore = retailStore;
    }
}
