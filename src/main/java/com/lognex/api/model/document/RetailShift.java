package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractFinance;
import com.lognex.api.model.base.AbstractOperation;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.RetailStore;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class RetailShift extends AbstractOperation implements IEntityWithAttributes {

    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
    private Date closeDate;
    private double proceedsNoCash;
    private double proceedsCash;
    private double receivedNoCash;
    private double receivedCash;
    private RetailStore retailStore;
    private List<AbstractOperation> operations = new ArrayList<>();
    private List<AbstractFinance> paymentOperations = new ArrayList<>();
}
