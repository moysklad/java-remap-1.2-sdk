package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractFinanceIn;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.*;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PaymentIn extends AbstractFinanceIn implements IEntityWithAttributes {
    private Organization organization;//!!!
    private Contract contract;
    private State state;
    private AgentAccount organizationAccount;
    private Agent agent;
    private AgentAccount agentAccount;
    private Set<Attribute<?>> attributes = new HashSet<>();
}
