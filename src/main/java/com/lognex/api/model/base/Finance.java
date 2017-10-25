package com.lognex.api.model.base;

import com.lognex.api.model.entity.ExpenseItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Finance extends Operation {
    private String paymentPurpose;
    private Double vatSum;
    private ExpenseItem expenseItem;
}
