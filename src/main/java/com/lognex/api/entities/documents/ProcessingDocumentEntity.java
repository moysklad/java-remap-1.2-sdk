package com.lognex.api.entities.documents;

import com.lognex.api.entities.StoreEntity;
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
}
