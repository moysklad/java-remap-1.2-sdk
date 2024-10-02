package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.positions.InternalOrderDocumentPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternalOrder extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    private LocalDateTime created;
    private String externalCode;
    private Organization organization;
    private ListEntity<InternalOrderDocumentPosition> positions;
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
    private List<DocumentAttribute> attributes;
    private LocalDateTime deliveryPlannedMoment;
    private List<PurchaseOrder> purchaseOrders;
    private List<Move> moves;
    private ListEntity<AttachedFile> files;

    public InternalOrder(String id) {
        super(id);
    }
}
