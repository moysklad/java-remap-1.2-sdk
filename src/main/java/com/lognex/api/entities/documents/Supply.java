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
public class Supply extends DocumentEntity implements IEntityWithAttributes {
    private Agent agent;
    private Contract contract;
    private LocalDateTime created;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private Organization organization;
    private Overhead overhead;
    private Long payedSum;
    private ListEntity<DocumentPosition> positions;
    private PurchaseOrder purchaseOrder;
    private Rate rate;
    private Store store;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private Project project;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private List<Attribute> attributes;
    private String incomingNumber;
    private LocalDateTime incomingDate;
    private FactureIn factureIn;
    private List<InvoiceIn> invoicesIn;
    private List<FinanceDocumentMarker> payments;
    private List<PurchaseReturn> returns;

    public Supply(String id) {
        super(id);
    }
}
