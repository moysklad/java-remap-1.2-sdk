package com.lognex.api.entities.documents;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.StateEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.AgentEntity;
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
    private Boolean applicable;
    private String description;
    private MetaEntity documents;
    private String externalCode;
    private LocalDateTime moment;
    private AgentEntity organization;
    private ListEntity positions;
    private ProcessingPlanDocumentEntity processingPlan;
    private Double quantity;
    private StateEntity state;
    private StoreEntity store;
}
