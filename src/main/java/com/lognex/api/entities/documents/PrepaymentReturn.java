package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.positions.PrepaymentDocumentPosition;
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
public class PrepaymentReturn extends DocumentEntity implements IEntityWithAttributes {
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Rate rate;
    private Agent agent;
    private Organization organization;
    private State state;
    private List<Attribute> attributes;
    private LocalDateTime created;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private RetailStore retailStore;
    private RetailShift retailShift;
    private Prepayment prepayment;
    private Long cashSum;
    private Long noCashSum;
    private TaxSystem taxSystem;
    private ListEntity<PrepaymentDocumentPosition> positions;

    public PrepaymentReturn(String id) {
        super(id);
    }
}
