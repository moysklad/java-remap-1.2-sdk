package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Отгрузка
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DemandDocumentEntity extends DocumentEntity {
    private String id;
    private String accountId;
    private String syncId;
    private Integer version;
    private LocalDateTime updated;
    private LocalDateTime deleted;
    private LocalDateTime created;
    private String name;
    private String description;
    private String externalCode;
    private LocalDateTime moment;
    private Boolean applicable;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Integer sum;
    private RateEntity rate;
    private EmployeeEntity owner;
    private Boolean shared;
    private MetaEntity group;
    private OrganizationEntity organization;
    private AgentEntity agent;
    private StoreEntity store;
    private ContractEntity contract;
    private MetaEntity project;
    private StateEntity state;
    private MetaEntity organizationAccount;
    private MetaEntity agentAccount;
    private MetaEntity attributes;
    private MetaEntity documents;
    private MetaEntity positions;
    private Integer vatSum;
    private Integer payedSum;
}
