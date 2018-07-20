package com.lognex.api.entities.documents;

import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.StateEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
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
public class ProcessingOrderDocumentEntity extends DocumentEntity {
    private EmployeeEntity owner;
    private Boolean shared;
    private Integer quantity;
    private String externalCode;
    private String description;
    private StateEntity state;
    private MetaEntity documents;
    private Boolean applicable;
    private ListEntity<DocumentPosition> positions;
    private StoreEntity store;
    private Integer version;
    private LocalDateTime moment;
    private ProcessingPlanDocumentEntity processingPlan;
    private String accountId;
    private AgentEntity organization;
    private String name;
    private String id;
    private LocalDateTime updated;
    private GroupEntity group;
}
