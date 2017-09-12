package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cashier extends AbstractEntity {
    private Employee employee;
    private RetailStore retailStore;
}
