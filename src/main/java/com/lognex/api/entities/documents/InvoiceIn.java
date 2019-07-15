package com.lognex.api.entities.documents;

import com.lognex.api.entities.Rate;
import com.lognex.api.entities.Store;
import com.lognex.api.entities.agents.Agent;
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
public class InvoiceIn extends DocumentEntity {
    private Agent agent;
    private LocalDateTime created;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private String incomingNumber;
    private Organization organization;
    private Long payedSum;
    private ListEntity<DocumentPosition> positions;
    private Rate rate;
    private Long shippedSum;
    private Store store;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;

    public InvoiceIn(String id) {
        super(id);
    }
}
