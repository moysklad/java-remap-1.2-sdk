package com.lognex.api.entities.documents;

import com.lognex.api.entities.AgentAccount;
import com.lognex.api.entities.Project;
import com.lognex.api.entities.State;
import com.lognex.api.entities.Store;
import com.lognex.api.entities.agents.Agent;
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
public class ProcessingOrder extends DocumentEntity {
    private String description;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private Agent organization;
    private ListEntity<DocumentPosition> positions;
    private ProcessingPlan processingPlan;
    private Double quantity;
    private State state;
    private Store store;
    private String syncId;
    private LocalDateTime deleted;
    private AgentAccount organizationAccount;
    private LocalDateTime deliveryPlannedMoment;
    private Project project;
    private List<Processing> processings;

    public ProcessingOrder(String id) {
        super(id);
    }
}
