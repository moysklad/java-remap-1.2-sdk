package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractFinance;
import com.lognex.api.model.base.AbstractOperation;
import com.lognex.api.model.base.ComingOutOperation;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.model.entity.money.OverheadWithDistribution;
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
public class Supply extends ComingOutOperation implements IEntityWithAttributes {

    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
    private OverheadWithDistribution overhead;
    private double payedSum;

    private PurchaseOrder purchaseOrder;
    private FactureIn factureIn;
    private InvoiceIn invoicesIn;
    private List<AbstractFinance> payments = new ArrayList<>();
    private List<AbstractOperation> returns = new ArrayList<>();
}
