package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractFinance;
import com.lognex.api.model.base.ComingOutOperation;
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
public class CustomerOrder extends ComingOutOperation implements IEntityWithAttributes {

    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private Set<Attribute<?>> attributes= new HashSet<>();
    private String documents;
    private double reservedSum;
    private Date deliveryPlannedMoment;
    private double payedSum;
    private double shippedSum;
    private double invoicedSum;

    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();
    private List<Demand> demands = new ArrayList<>();
    private List<AbstractFinance> payments = new ArrayList<>();
    private List<InvoiceOut> invoicesOut = new ArrayList<>();

    @Override
    public Attribute<?> getAttribute(String attributeId) {
        return attributes.stream().filter(a -> a.getId().equals(attributeId))
                .findFirst().orElseGet(null);
    }
}
