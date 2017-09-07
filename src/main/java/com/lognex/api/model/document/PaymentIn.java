package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractFinanceIn;
import com.lognex.api.model.entity.Organization;

public class PaymentIn extends AbstractFinanceIn {
    private Organization agent;//!!!
    //contract
    //state
    //organizationAccount
    //agentAccount
    //attributes

    public Organization getAgent() {
        return agent;
    }

    public void setAgent(Organization agent) {
        this.agent = agent;
    }
}
