package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceDocumentMarker;
import ru.moysklad.remap_1_2.entities.documents.positions.SupplyDocumentPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Supply extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    private Agent agent;
    private Contract contract;
    private LocalDateTime created;
    private String externalCode;
    private Organization organization;
    private Overhead overhead;
    private Long payedSum;
    private ListEntity<SupplyDocumentPosition> positions;
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
    private State state;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private List<DocumentAttribute> attributes;
    private String incomingNumber;
    private LocalDateTime incomingDate;
    private FactureIn factureIn;
    private List<InvoiceIn> invoicesIn;
    private List<FinanceDocumentMarker> payments;
    private List<PurchaseReturn> returns;
    private ListEntity<AttachedFile> files;

    public Supply(String id) {
        super(id);
    }
}
