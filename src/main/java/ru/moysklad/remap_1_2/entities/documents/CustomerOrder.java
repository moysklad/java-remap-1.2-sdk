package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceDocumentMarker;
import ru.moysklad.remap_1_2.entities.documents.positions.CustomerOrderDocumentPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Заказ Покупателя
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerOrder extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
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
    private List<DocumentAttribute> attributes;
    private LocalDateTime deliveryPlannedMoment;
    private Project project;
    private List<PurchaseOrder> purchaseOrders;
    private List<Demand> demands;
    private List<FinanceDocumentMarker> payments;
    private List<InvoiceOut> invoicesOut;
    private TaxSystem taxSystem;
    private ListEntity<AttachedFile> files;

    public CustomerOrder(String id) {
        super(id);
    }
}
