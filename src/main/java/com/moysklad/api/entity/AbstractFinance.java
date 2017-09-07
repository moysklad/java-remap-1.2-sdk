package com.moysklad.api.entity;

import com.moysklad.api.util.ID;

import java.util.Date;

public abstract class AbstractFinance extends AbstractOperation {
    private String paymentPurpose;
    private Double vatSum;
    private ID expenseItem;
    private String incomingNumber;
    private Date incomingDate;
}
