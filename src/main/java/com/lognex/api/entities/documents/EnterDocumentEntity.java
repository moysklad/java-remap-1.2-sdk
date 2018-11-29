package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.OrganizationEntity;
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
public class EnterDocumentEntity extends DocumentEntity {
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private OrganizationEntity organization;
    private ListEntity<DocumentPosition> positions;
    private RateEntity rate;
    private StateEntity state;
    private StoreEntity store;
    private String syncId;
    private ProjectEntity project;
    private List<AttributeEntity> attributes;
    private Overhead overhead;
}
