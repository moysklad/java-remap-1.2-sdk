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
public class SalesReturnDocumentEntity extends DocumentEntity {
    private Boolean vatEnabled;
    private Boolean shared;
    private AgentEntity agent;
    private String externalCode;
    private MetaEntity documents;
    private Long sum;
    private Long vatSum;
    private LocalDateTime moment;
    private String description;
    private RateEntity rate;
    private String id;
    private GroupEntity group;
    private OwnerEntity owner;
    private LocalDateTime created;
    private Boolean applicable;
    private ListEntity<DocumentPosition> positions;
    private StoreEntity store;
    private Integer version;
    private DemandDocumentEntity demand;
    private String accountId;
    private OrganizationEntity organization;
    private String name;
    private Boolean vatIncluded;
    private Long payedSum;
    private LocalDateTime updated;
}
