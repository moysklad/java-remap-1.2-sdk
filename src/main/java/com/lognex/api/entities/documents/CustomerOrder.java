package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.markers.FinanceDocumentMarker;
import com.lognex.api.entities.documents.positions.CustomerOrderDocumentPosition;
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
public class CustomerOrder extends DocumentEntity implements IEntityWithAttributes {
    private Agent agent;
    private Contract contract;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Long invoicedSum;
    private Organization organization;
    private Long payedSum;
    private ListEntity<CustomerOrderDocumentPosition> positions;
    private Rate rate;
    private Long reservedSum;
    private Long shippedSum;
    private State state;
    private Store store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private List<Attribute> attributes;
    private LocalDateTime deliveryPlannedMoment;
    private Project project;
    private List<PurchaseOrder> purchaseOrders;
    private List<Demand> demands;
    private List<FinanceDocumentMarker> payments;
    private List<InvoiceOut> invoicesOut;
    private TaxSystem taxSystem;

    public CustomerOrder(String id) {
        super(id);
    }
}
