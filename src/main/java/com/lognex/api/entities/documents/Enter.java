package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.positions.EnterDocumentPosition;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Оприходование
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Enter extends DocumentEntity implements IEntityWithAttributes {
    private Contract contract;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Organization organization;
    private ListEntity<EnterDocumentPosition> positions;
    private Rate rate;
    private State state;
    private Store store;
    private String syncId;
    private Project project;
    private List<Attribute> attributes;
    private Overhead overhead;

    public Enter(String id) {
        super(id);
    }
}
