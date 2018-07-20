package com.lognex.api.entities;

import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.documents.DocumentEntity;
import com.lognex.api.entities.documents.DocumentPosition;
import com.lognex.api.entities.documents.RetailDemandDocumentEntity;
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
public class RetailSalesReturnEntity extends DocumentEntity {
    private Boolean vatEnabled;
    private Boolean shared;
    private AgentEntity agent;
    private String externalCode;
    private MetaEntity documents;
    private Long sum;
    private Integer vatSum;
    private String syncId;
    private LocalDateTime moment;
    private RateEntity rate;
    private String id;
    private RetailShiftEntity retailShift;
    private GroupEntity group;
    private EmployeeEntity owner;
    private String created;
    private Boolean applicable;
    private ListEntity<DocumentPosition> positions;
    private StoreEntity store;
    private Integer version;
    private RetailDemandDocumentEntity demand;
    private String accountId;
    private AgentEntity organization;
    private String name;
    private Boolean vatIncluded;
    private RetailStoreEntity retailStore;
    private LocalDateTime updated;
    private Integer cashSum;
    private Integer noCashSum;
}
