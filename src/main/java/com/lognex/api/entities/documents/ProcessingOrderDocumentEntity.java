package com.lognex.api.entities.documents;

import com.lognex.api.entities.AccountEntity;
import com.lognex.api.entities.ProjectEntity;
import com.lognex.api.entities.StateEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.AgentEntity;
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
    private String syncId;
    private LocalDateTime deleted;
    private AccountEntity organizationAccount;
    private LocalDateTime deliveryPlannedMoment;
    private ProjectEntity project;
    private List<ProcessingDocumentEntity> processings;

    public ProcessingOrderDocumentEntity(String id) {
        super(id);
    }
}
