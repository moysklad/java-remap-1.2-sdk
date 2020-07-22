package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.markers.FinanceDocumentMarker;
import com.lognex.api.entities.documents.positions.PurchaseOrderDocumentPosition;
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
public class PurchaseOrder extends DocumentEntity implements IEntityWithAttributes {
    private Agent agent;
    private Contract contract;
    private LocalDateTime created;
    private String description;
    private String externalCode;
    private Long invoicedSum;
    private Organization organization;
    private Long payedSum;
    private ListEntity<PurchaseOrderDocumentPosition> positions;
    private Rate rate;
    private Long shippedSum;
    private State state;
    private Store store;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private Long waitSum;
    private String syncId;
    private LocalDateTime deleted;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private List<Attribute> attributes;
    private LocalDateTime deliveryPlannedMoment;
    private Project project;
    private List<CustomerOrder> customerOrders;
    private List<InvoiceIn> invoicesIn;
    private List<FinanceDocumentMarker> payments;
    private List<Supply> supplies;
    private InternalOrder internalOrder;

    public PurchaseOrder(String id) {
        super(id);
    }
}
