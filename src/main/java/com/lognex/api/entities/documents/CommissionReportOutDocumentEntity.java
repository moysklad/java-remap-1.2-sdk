package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommissionReportOutDocumentEntity extends DocumentEntity {
    private AgentEntity agent;
    private LocalDateTime commissionPeriodEnd;
    private LocalDateTime commissionPeriodStart;
    private Long commitentSum;
    private ContractEntity contract;
    private LocalDateTime created;
    private MetaEntity documents;
    private String externalCode;
    private OrganizationEntity organization;
    private MetaEntity organizationAccount;
    private Long payedSum;
    private ListEntity positions;
    private RateEntity rate;
    private Double rewardPercent;
    private RewardType rewardType;
    private StateEntity state;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
}
