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
public class PurchaseOrderDocumentEntity extends DocumentEntity implements IEntityWithAttributes {
    private AgentEntity agent;
    private ContractEntity contract;
    private LocalDateTime created;
    private String description;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private Long invoicedSum;
    private OrganizationEntity organization;
    private Long payedSum;
    private ListEntity<DocumentPosition> positions;
    private RateEntity rate;
    private Long shippedSum;
    private StateEntity state;
    private StoreEntity store;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private Long waitSum;
    private String syncId;
    private LocalDateTime deleted;
    private AccountEntity organizationAccount;
    private AccountEntity agentAccount;
    private List<AttributeEntity> attributes;
    private LocalDateTime deliveryPlannedMoment;
    private ProjectEntity project;
    private List<CustomerOrderDocumentEntity> customerOrders;
    private List<InvoiceInDocumentEntity> invoicesIn;
    private List<FinanceDocumentMarker> payments;
    private List<SupplyDocumentEntity> supplies;
    private InternalOrderDocumentEntity internalOrder;

    public PurchaseOrderDocumentEntity(String id) {
        super(id);
    }
}
