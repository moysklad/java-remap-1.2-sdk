package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.IEntityWithAttributes;
import ru.moysklad.remap_1_2.entities.State;
import ru.moysklad.remap_1_2.entities.Store;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.positions.InventoryDocumentPosition;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Inventory extends DocumentEntity implements IEntityWithAttributes {
    private List<Attribute> attributes;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String externalCode;
    private Organization organization;
    private ListEntity<InventoryDocumentPosition> positions;
    private State state;
    private Store store;
    private String syncId;
    private String description;

    public Inventory(String id) {
        super(id);
    }
}
