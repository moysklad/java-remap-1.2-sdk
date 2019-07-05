package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetailSalesReturnEntity extends DocumentEntity implements IEntityWithAttributes {
    private AgentEntity agent;
    private Long cashSum;
    private LocalDateTime created;
    private RetailDemandDocumentEntity demand;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private Long noCashSum;
    private AgentEntity organization;
    private ListEntity<DocumentPosition> positions;
    private RateEntity rate;
    private RetailShiftDocumentEntity retailShift;
    private RetailStoreEntity retailStore;
    private StoreEntity store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private LocalDateTime deleted;
    private String description;
    private ProjectEntity project;
    private AccountEntity organizationAccount;
    private AccountEntity agentAccount;
    private List<AttributeEntity> attributes;

    public RetailSalesReturnEntity(String id) {
        super(id);
    }
}
