package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
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
public class InvoiceOut extends DocumentEntity {
    private Agent agent;
    private AgentAccount agentAccount;
    private List<Attribute> attributes;
    private Contract contract;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Organization organization;
    private AgentAccount organizationAccount;
    private Long payedSum;
    private LocalDateTime paymentPlannedMoment;
    private ListEntity<DocumentPosition> positions;
    private Project project;
    private Rate rate;
    private Long shippedSum;
    private State state;
    private Store store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;

    public InvoiceOut(String id) {
        super(id);
    }
}
