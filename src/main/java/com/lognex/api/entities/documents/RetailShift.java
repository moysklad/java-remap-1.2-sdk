package com.lognex.api.entities.documents;

import com.lognex.api.entities.RetailStore;
import com.lognex.api.entities.Store;
import com.lognex.api.entities.agents.Organization;
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
public class RetailShift extends DocumentEntity {
    private String externalCode;
    private ListEntity<DocumentEntity> documents;
    private LocalDateTime created;
    private LocalDateTime closeDate;
    private Double receivedCash;
    private Store store;
    private String syncId;
    private Double proceedsNoCash;
    private Organization organization;
    private RetailStore retailStore;
    private Double proceedsCash;
    private Double receivedNoCash;

    public RetailShift(String id) {
        super(id);
    }
}
