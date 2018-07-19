package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
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
public class LossDocumentEntity extends DocumentEntity {
    private OwnerEntity owner;
    private Boolean shared;
    private String externalCode;
    private MetaEntity documents;
    private LocalDateTime created;
    private Boolean applicable;
    private Long sum;
    private ListEntity<DocumentPosition> positions;
    private StoreEntity store;
    private Integer version;
    private LocalDateTime moment;
    private String accountId;
    private String description;
    private StateEntity state;
    private RateEntity rate;
    private AgentEntity organization;
    private String name;
    private String id;
    private LocalDateTime updated;
    private GroupEntity group;
}
