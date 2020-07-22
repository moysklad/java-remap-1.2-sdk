package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.documents.positions.RetailSalesDocumentPosition;
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
public class RetailSalesReturn extends DocumentEntity implements IEntityWithAttributes {
    private Agent agent;
    private Long cashSum;
    private LocalDateTime created;
    private RetailDemand demand;
    private String externalCode;
    private Long noCashSum;
    private Agent organization;
    private ListEntity<RetailSalesDocumentPosition> positions;
    private Rate rate;
    private RetailShift retailShift;
    private RetailStore retailStore;
    private Store store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private LocalDateTime deleted;
    private String description;
    private Project project;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private List<Attribute> attributes;
    private Contract contract;
    private State state;
    private TaxSystem taxSystem;

    public RetailSalesReturn(String id) {
        super(id);
    }
}
