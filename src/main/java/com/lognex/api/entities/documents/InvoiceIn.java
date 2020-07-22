package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.positions.InvoiceDocumentPosition;
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
public class InvoiceIn extends DocumentEntity {
    private Agent agent;
    private AgentAccount agentAccount;
    private Contract contract;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private String incomingNumber;
    private LocalDateTime incomingDate;
    private Organization organization;
    private AgentAccount organizationAccount;
    private Long payedSum;
    private LocalDateTime paymentPlannedMoment;
    private ListEntity<InvoiceDocumentPosition> positions;
    private Rate rate;
    private Long shippedSum;
    private State state;
    private Store store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;

    public InvoiceIn(String id) {
        super(id);
    }
}
