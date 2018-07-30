package com.lognex.api.entities.documents;

import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.StoreEntity;
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
public class ProcessingDocumentEntity extends DocumentEntity {
    private String id;
    private String accountId;
    private EmployeeEntity owner;
    private Boolean shared;
    private GroupEntity group;
    private Integer version;
    private LocalDateTime updated;
    private String name;
    private String externalCode;
    private LocalDateTime moment;
    private Boolean applicable;
    private OrganizationEntity organization;
    private MetaEntity documents;
    private LocalDateTime created;
    private Long processingSum;
    private Double quantity;
    private ProcessingPlanDocumentEntity processingPlan;
    private StoreEntity productsStore;
    private StoreEntity materialsStore;
    private ListEntity<DocumentPosition> products;
    private ListEntity<DocumentPosition> materials;
}
