package com.lognex.api.model.document;

import com.lognex.api.model.base.Operation;
import com.lognex.api.model.base.OperationWithPositions;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.Store;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public abstract class Invoice extends OperationWithPositions implements IEntityWithAttributes {

    private Store store;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
    private Date paymentPlannedMoment;
    private double payedSum;
    private double shippedSum;
    private List<Operation> operations;
}
