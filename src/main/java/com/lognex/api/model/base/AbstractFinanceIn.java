package com.lognex.api.model.base;

import java.util.Set;

public abstract class AbstractFinanceIn extends AbstractFinance {
    private AbstractOperation invoiceOut;
    private AbstractOperation customerOrder;
    private Set<AbstractOperation> demands;
    private AbstractOperation purchaseReturn;
    //factureOut
    private AbstractOperation comissionReportIn;

    public AbstractOperation getInvoiceOut() {
        return invoiceOut;
    }

    public void setInvoiceOut(AbstractOperation invoiceOut) {
        this.invoiceOut = invoiceOut;
    }

    public AbstractOperation getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(AbstractOperation customerOrder) {
        this.customerOrder = customerOrder;
    }

    public Set<AbstractOperation> getDemands() {
        return demands;
    }

    public void setDemands(Set<AbstractOperation> demands) {
        this.demands = demands;
    }

    public AbstractOperation getPurchaseReturn() {
        return purchaseReturn;
    }

    public void setPurchaseReturn(AbstractOperation purchaseReturn) {
        this.purchaseReturn = purchaseReturn;
    }

    public AbstractOperation getComissionReportIn() {
        return comissionReportIn;
    }

    public void setComissionReportIn(AbstractOperation comissionReportIn) {
        this.comissionReportIn = comissionReportIn;
    }
}
