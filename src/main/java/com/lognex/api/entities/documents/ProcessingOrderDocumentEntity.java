package com.lognex.api.entities.documents;

import com.lognex.api.entities.StateEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessingOrderDocumentEntity extends DocumentEntity {
    private String description;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private AgentEntity organization;
    private ListEntity<DocumentPosition> positions;
    private ProcessingPlanDocumentEntity processingPlan;
    private Double quantity;
    private StateEntity state;
    private StoreEntity store;
}
