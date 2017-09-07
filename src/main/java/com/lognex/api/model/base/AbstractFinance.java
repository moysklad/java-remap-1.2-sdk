package com.lognex.api.model.base;

import com.lognex.api.util.ID;

import java.util.Date;

public abstract class AbstractFinance extends AbstractOperation {
    private String paymentPurpose;
    private Double vatSum;
    private ID expenseItem;
    private String incomingNumber;
    private Date incomingDate;

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public Double getVatSum() {
        return vatSum;
    }

    public void setVatSum(Double vatSum) {
        this.vatSum = vatSum;
    }

    public ID getExpenseItem() {
        return expenseItem;
    }

    public void setExpenseItem(ID expenseItem) {
        this.expenseItem = expenseItem;
    }

    public String getIncomingNumber() {
        return incomingNumber;
    }

    public void setIncomingNumber(String incomingNumber) {
        this.incomingNumber = incomingNumber;
    }

    public Date getIncomingDate() {
        return incomingDate;
    }

    public void setIncomingDate(Date incomingDate) {
        this.incomingDate = incomingDate;
    }
}
