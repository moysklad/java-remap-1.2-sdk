package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
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
    private String accountId;
    private Boolean applicable;
    private LocalDateTime created;
    private String description;
    private MetaEntity documents;
    private String externalCode;
    private GroupEntity group;
    private String id;
    private LocalDateTime moment;
    private String name;
    private AgentEntity organization;
    private EmployeeEntity owner;
    private ListEntity positions;
    private RateEntity rate;
    private Boolean shared;
    private StateEntity state;
    private StoreEntity store;
    private Long sum;
    private LocalDateTime updated;
    private Integer version;
}
