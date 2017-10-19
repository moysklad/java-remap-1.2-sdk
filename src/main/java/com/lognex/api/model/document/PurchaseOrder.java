package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractFinance;
import com.lognex.api.model.base.AbstractOperationWithPositions;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrder extends AbstractOperationWithPositions implements IEntityWithAttributes {

    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
    private Date deliveryPlannedMoment;
    private double payedSum;
    private double shippedSum;
    private double invoicedSum;
    private double waitSum;

    private List<CustomerOrder> customerOrders = new ArrayList<>();
    private List<InvoiceIn> invoicesIn = new ArrayList<>();
    private List<AbstractFinance> payments = new ArrayList<>();
    private List<Supply> supplies = new ArrayList<>();
    private InternalOrder internalOrder;
}
