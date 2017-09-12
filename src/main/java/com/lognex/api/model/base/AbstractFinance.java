package com.lognex.api.model.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class AbstractFinance extends AbstractOperation {
    private String paymentPurpose;
    private Double vatSum;
    //private ID expenseItem;
    private String incomingNumber;
    private Date incomingDate;
}
