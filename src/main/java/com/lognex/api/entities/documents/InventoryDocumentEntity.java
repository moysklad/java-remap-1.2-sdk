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
public class InventoryDocumentEntity extends DocumentEntity {
    private EmployeeEntity owner;
    private Boolean shared;
    private String externalCode;
    private MetaEntity documents;
    private Long sum;
    private ListEntity<DocumentPosition> positions;
    private StoreEntity store;
    private Integer version;
    private LocalDateTime moment;
    private String accountId;
    private OrganizationEntity organization;
    private String name;
    private String id;
    private LocalDateTime updated;
    private GroupEntity group;
}
