package com.lognex.api.model.document;

import com.lognex.api.model.base.Operation;
import com.lognex.api.model.base.OperationWithPositions;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseReturn extends OperationWithPositions implements IEntityWithAttributes {

    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;

    private Supply supply;
    private FactureOut factureOut;
    private double payedSum;
    private List<Operation> payments = new ArrayList<>();
}
