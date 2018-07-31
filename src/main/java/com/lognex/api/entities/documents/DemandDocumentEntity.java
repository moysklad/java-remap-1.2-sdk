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

/**
 * Отгрузка
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DemandDocumentEntity extends DocumentEntity {
    private String accountId;
    private AgentEntity agent;
    private MetaEntity agentAccount;
    private Boolean applicable;
    private MetaEntity attributes;
    private ContractEntity contract;
    private LocalDateTime created;
    private CustomerOrderDocumentEntity customerOrder;
    private LocalDateTime deleted;
    private String description;
    private MetaEntity documents;
    private String externalCode;
    private MetaEntity group;
    private String id;
    private LocalDateTime moment;
    private String name;
    private OrganizationEntity organization;
    private MetaEntity organizationAccount;
    private Overhead overhead;
    private EmployeeEntity owner;
    private Integer payedSum;
    private ListEntity positions;
    private MetaEntity project;
    private RateEntity rate;
    private Boolean shared;
    private StateEntity state;
    private StoreEntity store;
    private Long sum;
    private String syncId;
    private LocalDateTime updated;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Integer vatSum;
    private Integer version;
}
