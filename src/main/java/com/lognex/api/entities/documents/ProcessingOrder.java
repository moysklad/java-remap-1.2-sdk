package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.documents.positions.ProcessingOrderPosition;
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
public class ProcessingOrder extends DocumentEntity implements IEntityWithAttributes {
    private List<Attribute> attributes;
    private String description;
    private String externalCode;
    private Agent organization;
    private ListEntity<ProcessingOrderPosition> positions;
    private ProcessingPlan processingPlan;
    private Double quantity;
    private State state;
    private Store store;
    private String syncId;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private AgentAccount organizationAccount;
    private LocalDateTime deliveryPlannedMoment;
    private Project project;
    private List<Processing> processings;

    public ProcessingOrder(String id) {
        super(id);
    }
}
