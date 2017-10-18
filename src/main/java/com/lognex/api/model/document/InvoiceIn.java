package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractOperation;
import com.lognex.api.model.base.AbstractOperationWithPositions;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.Store;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceIn extends AbstractOperationWithPositions implements IEntityWithAttributes {

    private Store store;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
    private Date paymentPlannedMoment;
    private double payedSum;
    private double shippedSum;

    private PurchaseOrder purchaseOrder;
    private List<AbstractOperation> operations;
    private List<Supply> supplies;

    @Override
    public Attribute<?> getAttribute(String attributeId) {
        return attributes.stream().filter(a -> a.getId().equals(attributeId))
                .findFirst().orElseGet(null);
    }
}