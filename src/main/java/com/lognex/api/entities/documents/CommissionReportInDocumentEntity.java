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
public class CommissionReportInDocumentEntity extends DocumentEntity {
    private String accountId;
    private AgentEntity agent;
    private Boolean applicable;
    private LocalDateTime commissionPeriodEnd;
    private LocalDateTime commissionPeriodStart;
    private Long commitentSum;
    private ContractEntity contract;
    private String created;
    private MetaEntity documents;
    private String externalCode;
    private GroupEntity group;
    private String id;
    private LocalDateTime moment;
    private String name;
    private OrganizationEntity organization;
    private EmployeeEntity owner;
    private Integer payedSum;
    private ListEntity positions;
    private RateEntity rate;
    private Double rewardPercent;
    private RewardType rewardType;
    private Boolean shared;
    private StateEntity state;
    private Long sum;
    private LocalDateTime updated;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private Integer version;
}
