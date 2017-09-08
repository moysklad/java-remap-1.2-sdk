package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractFinanceIn;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.Organization;

public class PaymentIn extends AbstractFinanceIn {
    private Organization organization;//!!!
    //contract
    //state
    //organizationAccount
    private AgentAccount agentAccount;
    //attributes

    public Organization getAgent() {
        return organization;
    }

    public void setAgent(Organization organization) {
        this.organization = organization;
    }

    public AgentAccount getAgentAccount() {
        return agentAccount;
    }

    public void setAgentAccount(AgentAccount agentAccount) {
        this.agentAccount = agentAccount;
    }
}
