package com.lognex.api.entities.documents;

import com.lognex.api.entities.RetailStoreEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetailShiftDocumentEntity extends DocumentEntity {
    private String externalCode;
    private ListEntity<DocumentEntity> documents;
    private LocalDateTime created;
    private LocalDateTime closeDate;
    private Double receivedCash;
    private StoreEntity store;
    private String syncId;
    private Double proceedsNoCash;
    private OrganizationEntity organization;
    private RetailStoreEntity retailStore;
    private Double proceedsCash;
    private Double receivedNoCash;

    public RetailShiftDocumentEntity(String id) {
        super(id);
    }
}
