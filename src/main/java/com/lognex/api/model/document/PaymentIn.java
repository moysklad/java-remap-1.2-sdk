package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractFinanceIn;
import com.lognex.api.model.entity.Agent;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.Organization;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentIn extends AbstractFinanceIn {
    private Organization organization;//!!!
    //contract
    //state
    //organizationAccount
    private AgentAccount agentAccount;
    //attributes
}
