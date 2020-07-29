package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.documents.positions.LossDocumentPosition;
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
public class Loss extends DocumentEntity implements IEntityWithAttributes {
    private Agent agent;
    private List<Attribute> attributes;
    private LocalDateTime created;
    private Contract contract;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Agent organization;
    private ListEntity<LossDocumentPosition> positions;
    private Project project;
    private Rate rate;
    private State state;
    private Store store;
    private String syncId;
    private SalesReturn salesReturn;
    private Boolean vatEnabled;
    private Boolean vatIncluded;

    public Loss(String id) {
        super(id);
    }
}
