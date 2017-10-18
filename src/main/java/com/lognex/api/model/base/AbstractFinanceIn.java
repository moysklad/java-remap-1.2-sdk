package com.lognex.api.model.base;

import com.lognex.api.model.document.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public abstract class AbstractFinanceIn extends AbstractFinance {
    private InvoiceOut invoiceOut;
    private CustomerOrder customerOrder;
    private Set<Demand> demands;
    private PurchaseReturn purchaseReturn;
    private FactureOut factureOut;
    private CommissionReportIn comissionReportIn;
}
