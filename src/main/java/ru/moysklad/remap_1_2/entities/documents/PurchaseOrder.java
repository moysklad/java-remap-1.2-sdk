package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceDocumentMarker;
import ru.moysklad.remap_1_2.entities.documents.positions.PurchaseOrderDocumentPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PurchaseOrder extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
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
    private List<DocumentAttribute> attributes;
    private LocalDateTime deliveryPlannedMoment;
    private Project project;
    private List<CustomerOrder> customerOrders;
    private List<InvoiceIn> invoicesIn;
    private List<FinanceDocumentMarker> payments;
    private List<Supply> supplies;
    private InternalOrder internalOrder;
    private ListEntity<AttachedFile> files;

    public PurchaseOrder(String id) {
        super(id);
    }
}
