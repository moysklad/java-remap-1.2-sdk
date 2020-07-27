package com.lognex.api.entities.documents;

import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.IEntityWithAttributes;
import com.lognex.api.entities.State;
import com.lognex.api.entities.Store;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.positions.InventoryDocumentPosition;
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

    public Inventory(String id) {
        super(id);
    }
}
