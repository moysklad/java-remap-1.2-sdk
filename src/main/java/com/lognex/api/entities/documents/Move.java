package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.documents.positions.MoveDocumentPosition;
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
public class Move extends DocumentEntity implements IEntityWithAttributes {
    private List<Attribute> attributes;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Agent organization;
    private Overhead overhead;
    private ListEntity<MoveDocumentPosition> positions;
    private Project project;
    private Rate rate;
    private Store sourceStore;
    private State state;
    private String syncId;
    private Store targetStore;
    private InternalOrder internalOrder;

    public Move(String id) {
        super(id);
    }
}
