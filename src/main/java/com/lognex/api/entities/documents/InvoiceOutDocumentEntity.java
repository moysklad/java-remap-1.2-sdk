package com.lognex.api.entities.documents;

import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.StoreEntity;
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
public class InvoiceOutDocumentEntity extends DocumentEntity {
    private Boolean vatEnabled;
    private Boolean shared;
    private AgentEntity agent;
    private String externalCode;
    private MetaEntity documents;
    private Long shippedSum;
    private Long sum;
    private Long vatSum;
    private LocalDateTime moment;
    private RateEntity rate;
    private String id;
    private GroupEntity group;
    private EmployeeEntity owner;
    private LocalDateTime created;
    private Boolean applicable;
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
