package com.lognex.api.model.document;

import com.lognex.api.model.base.Finance;
import com.lognex.api.model.base.OperationWithPositions;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.base.field.RewardType;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public abstract class CommissionReport extends OperationWithPositions implements IEntityWithAttributes {

    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private Set<Attribute<?>> attributes;
    private String documents;
    private double payedSum;
    private Date commissionPeriodStart;
    private Date commissionPeriodEnd;
    private RewardType rewardType;
    private double rewardPercent;
    private double commitentSum;

    private List<Finance> payments = new ArrayList<>();
}