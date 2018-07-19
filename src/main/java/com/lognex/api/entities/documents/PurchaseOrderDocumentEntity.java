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
public class PurchaseOrderDocumentEntity extends DocumentEntity {
    private Boolean vatEnabled;
    private Boolean shared;
    private AgentEntity agent;
    private ContractEntity contract;
    private String externalCode;
    private MetaEntity documents;
    private Long shippedSum;
    private Long sum;
    private Long vatSum;
    private LocalDateTime moment;
    private Long invoicedSum;
    private RateEntity rate;
    private String id;
    private String description;
    private GroupEntity group;
    private Long waitSum;
    private OwnerEntity owner;
    private LocalDateTime created;
    private Boolean applicable;
    private StateEntity state;
    private ListEntity<DocumentPosition> positions;
    private StoreEntity store;
    private Integer version;
    private String accountId;
    private OrganizationEntity organization;
    private String name;
    private Boolean vatIncluded;
    private Long payedSum;
    private LocalDateTime updated;
}
