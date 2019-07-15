package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Organization;
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
public class Processing extends DocumentEntity implements IEntityWithAttributes {
    private LocalDateTime created;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private ListEntity<DocumentPosition> materials;
    private Store materialsStore;
    private Organization organization;
    private ProcessingPlan processingPlan;
    private Long processingSum;
    private ListEntity<DocumentPosition> products;
    private Store productsStore;
    private Double quantity;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private AgentAccount organizationAccount;
    private State state;
    private List<Attribute> attributes;
    private Project project;
    private ProcessingOrder processingOrder;

    public Processing(String id) {
        super(id);
    }
}
