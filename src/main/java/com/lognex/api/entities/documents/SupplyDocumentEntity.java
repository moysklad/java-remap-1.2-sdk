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
public class SupplyDocumentEntity extends DocumentEntity {
    private EmployeeEntity owner;
    private Boolean vatEnabled;
    private Boolean shared;
    private AgentEntity agent;
    private String externalCode;
    private MetaEntity documents;
    private LocalDateTime created;
    private Boolean applicable;
    private Long sum;
    private ListEntity<DocumentPosition> positions;
    private Integer vatSum;
    private StoreEntity store;
    private Integer version;
    private LocalDateTime moment;
    private String accountId;
    private RateEntity rate;
    private OrganizationEntity organization;
    private String name;
    private Boolean vatIncluded;
    private String id;
    private Integer payedSum;
    private LocalDateTime updated;
    private GroupEntity group;
    private ContractEntity contract;
    private Overhead overhead;
}
