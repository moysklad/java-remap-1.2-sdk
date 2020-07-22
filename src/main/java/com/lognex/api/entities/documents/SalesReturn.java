package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.markers.FinanceDocumentMarker;
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
public class SalesReturn extends DocumentEntity implements IEntityWithAttributes {
    private Agent agent;
    private LocalDateTime created;
    private Demand demand;
    private String description;
    private String externalCode;
    private Organization organization;
    private Long payedSum;
    private ListEntity<DocumentPosition> positions;
    private Rate rate;
    private State state;
    private Store store;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private String syncId;
    private LocalDateTime deleted;
    private Contract contract;
    private Project project;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private List<Attribute> attributes;
    private List<Loss> losses;
    private List<FinanceDocumentMarker> payments;

    public SalesReturn(String id) {
        super(id);
    }
}
