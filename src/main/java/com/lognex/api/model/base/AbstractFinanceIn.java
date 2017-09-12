package com.lognex.api.model.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public abstract class AbstractFinanceIn extends AbstractFinance {
    private AbstractOperation invoiceOut;
    private AbstractOperation customerOrder;
    private Set<AbstractOperation> demands;
    private AbstractOperation purchaseReturn;
    //factureOut
    private AbstractOperation comissionReportIn;
}
