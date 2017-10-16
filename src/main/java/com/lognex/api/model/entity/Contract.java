package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityLegendable;
import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.model.entity.item.ContractType;
import com.lognex.api.model.entity.item.RewardType;

import java.util.List;

public class Contract extends AbstractEntityLegendable {

    private boolean archived;
    private ContractType contractType;
    private RewardType rewardType;
    private double rewardPercent;
    private Agent ownAgent;
    private Agent agent;
    private State state;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private Currency rate;
    private List<Attribute> attributes;
}
