package com.lognex.api.entities;

import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.documents.DocumentEntity;
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
public class RetailShiftEntity extends MetaEntity {
    private EmployeeEntity owner;
    private Boolean shared;
    private String externalCode;
    private ListEntity<DocumentEntity> documents;
    private LocalDateTime created;
    private LocalDateTime closeDate;
    private Double receivedCash;
    private Boolean applicable;
    private StoreEntity store;
    private Integer version;
    private String syncId;
    private LocalDateTime moment;
    private String accountId;
    private Double proceedsNoCash;
    private OrganizationEntity organization;
    private String name;
    private RetailStoreEntity retailStore;
    private String id;
    private Double proceedsCash;
    private Double receivedNoCash;
    private LocalDateTime updated;
    private GroupEntity group;
}
