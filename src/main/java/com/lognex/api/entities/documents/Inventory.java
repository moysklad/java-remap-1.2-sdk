package com.lognex.api.entities.documents;

import com.lognex.api.entities.Store;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Inventory extends DocumentEntity {
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private Organization organization;
    private ListEntity<DocumentPosition> positions;
    private Store store;

    public Inventory(String id) {
        super(id);
    }
}
