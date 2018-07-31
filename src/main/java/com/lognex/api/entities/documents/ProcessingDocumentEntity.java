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
    private String accountId;
    private Boolean applicable;
    private LocalDateTime created;
    private MetaEntity documents;
    private String externalCode;
    private GroupEntity group;
    private String id;
    private ListEntity materials;
    private StoreEntity materialsStore;
    private LocalDateTime moment;
    private String name;
    private OrganizationEntity organization;
    private EmployeeEntity owner;
    private ProcessingPlanDocumentEntity processingPlan;
    private Long processingSum;
    private ListEntity products;
    private StoreEntity productsStore;
    private Double quantity;
    private Boolean shared;
    private LocalDateTime updated;
    private Integer version;
}
