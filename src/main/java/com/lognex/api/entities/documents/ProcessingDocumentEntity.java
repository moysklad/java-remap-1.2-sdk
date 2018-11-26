package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.OrganizationEntity;
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
public class ProcessingDocumentEntity extends DocumentEntity {
    private LocalDateTime created;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private ListEntity<DocumentPosition> materials;
    private StoreEntity materialsStore;
    private OrganizationEntity organization;
    private ProcessingPlanDocumentEntity processingPlan;
    private Long processingSum;
    private ListEntity<DocumentPosition> products;
    private StoreEntity productsStore;
    private Double quantity;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private AccountEntity organizationAccount;
    private AccountEntity agentAccount;
    private StateEntity state;
    private List<AttributeEntity> attributes;
    private ProjectEntity project;
    private ProcessingOrderDocumentEntity processingOrder;
}
