package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.markers.FinanceOutDocumentMarker;
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
public class PaymentOut extends DocumentEntity implements FinanceOutDocumentMarker, IEntityWithAttributes {
    private Agent agent;
    private LocalDateTime created;
    private ListEntity<DocumentEntity> documents;
    private ExpenseItem expenseItem;
    private String externalCode;
    private Organization organization;
    private Project project;
    private Rate rate;
    private State state;
    private Long vatSum;
    private Contract contract;
    private String paymentPurpose;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private List<Attribute> attributes;
    private FactureIn factureIn;
    private List<MetaEntity> operations;

    public PaymentOut(String id) {
        super(id);
    }
}
