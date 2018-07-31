package com.lognex.api.entities.documents;

import com.lognex.api.entities.MetaEntity;
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
    private Boolean applicable;
    private LocalDateTime created;
    private MetaEntity documents;
    private String externalCode;
    private ListEntity materials;
    private StoreEntity materialsStore;
    private LocalDateTime moment;
    private OrganizationEntity organization;
    private ProcessingPlanDocumentEntity processingPlan;
    private Long processingSum;
    private ListEntity products;
    private StoreEntity productsStore;
    private Double quantity;
}
