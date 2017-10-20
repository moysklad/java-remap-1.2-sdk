package com.lognex.api.model.entity;

import com.lognex.api.model.base.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cashier extends Entity {
    private Employee employee;
    private RetailStore retailStore;
}
