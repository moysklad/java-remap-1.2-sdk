package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
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
public class SupplyDocumentEntity extends DocumentEntity {
    private AgentEntity agent;
    private ContractEntity contract;
    private LocalDateTime created;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private OrganizationEntity organization;
    private Overhead overhead;
    private Long payedSum;
    private ListEntity<DocumentPosition> positions;
    private PurchaseOrderDocumentEntity purchaseOrder;
    private RateEntity rate;
    private StoreEntity store;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private ProjectEntity project;
    private AccountEntity organizationAccount;
    private AccountEntity agentAccount;
    private List<AttributeEntity> attributes;
    private String incomingNumber;
    private LocalDateTime incomingDate;
    private FactureInDocumentEntity factureIn;
    private List<InvoiceInDocumentEntity> invoicesIn;
    private List<FinanceDocumentMarker> payments;
    private ListEntity<MetaEntity> returns;
}
