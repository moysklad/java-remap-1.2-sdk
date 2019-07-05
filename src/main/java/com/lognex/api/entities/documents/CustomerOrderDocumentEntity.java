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

/**
 * Заказ Покупателя
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerOrderDocumentEntity extends DocumentEntity implements IEntityWithAttributes {
    private AgentEntity agent;
    private ContractEntity contract;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private Long invoicedSum;
    private OrganizationEntity organization;
    private Long payedSum;
    private ListEntity<DocumentPosition> positions;
    private RateEntity rate;
    private Long reservedSum;
    private Long shippedSum;
    private StateEntity state;
    private StoreEntity store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private AccountEntity organizationAccount;
    private AccountEntity agentAccount;
    private List<AttributeEntity> attributes;
    private LocalDateTime deliveryPlannedMoment;
    private ProjectEntity project;
    private List<PurchaseOrderDocumentEntity> purchaseOrders;
    private List<DemandDocumentEntity> demands;
    private List<FinanceDocumentMarker> payments;
    private List<InvoiceOutDocumentEntity> invoicesOut;

    public CustomerOrderDocumentEntity(String id) {
        super(id);
    }
}
