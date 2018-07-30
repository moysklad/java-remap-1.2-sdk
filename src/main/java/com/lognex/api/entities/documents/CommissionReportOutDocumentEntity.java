package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
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
    private Boolean vatEnabled;
    private Boolean shared;
    private AgentEntity agent;
    private String externalCode;
    private StateEntity state;
    private MetaEntity documents;
    private RewardType rewardType;
    private Long commitentSum;
    private Long sum;
    private Long vatSum;
    private LocalDateTime moment;
    private MetaEntity organizationAccount;
    private LocalDateTime commissionPeriodStart;
    private Double rewardPercent;
    private RateEntity rate;
    private String id;
    private GroupEntity group;
    private EmployeeEntity owner;
    private LocalDateTime created;
    private ContractEntity contract;
    private Boolean applicable;
    private ListEntity<DocumentPosition> positions;
    private Integer version;
    private String accountId;
    private LocalDateTime commissionPeriodEnd;
    private OrganizationEntity organization;
    private String name;
    private Boolean vatIncluded;
    private Long payedSum;
    private LocalDateTime updated;
}
