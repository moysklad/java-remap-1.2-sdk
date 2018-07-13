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
public class RetailDemandDocumentEntity extends DocumentEntity {
    private Boolean vatEnabled;
    private Boolean shared;
    private Integer noCashSum;
    private AgentEntity agent;
    private String externalCode;
    private MetaEntity documents;
    private Long sum;
    private Integer vatSum;
    private Integer cashSum;
    private String syncId;
    private LocalDateTime moment;
    private RateEntity rate;
    private String id;
    private RetailShiftEntity retailShift;
    private GroupEntity group;
    private EmployeeEntity owner;
    private LocalDateTime created;
    private Boolean applicable;
    private ListEntity<DocumentPosition> positions;
    private StoreEntity store;
    private Integer version;
    private String accountId;
    private Boolean fiscal;
    private OrganizationEntity organization;
    private String name;
    private Boolean vatIncluded;
    private RetailStoreEntity retailStore;
    private Integer payedSum;
    private LocalDateTime updated;
}
