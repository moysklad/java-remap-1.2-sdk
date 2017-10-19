package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityLegendable;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.base.field.ContractType;
import com.lognex.api.model.base.field.RewardType;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Contract extends AbstractEntityLegendable implements IEntityWithAttributes {

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
    private Set<Attribute<?>> attributes = new HashSet<>();
}
