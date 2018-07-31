package com.lognex.api.entities.documents;

import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.StoreEntity;
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
public class PurchaseReturnDocumentEntity extends DocumentEntity {
    private String accountId;
    private AgentEntity agent;
    private Boolean applicable;
    private LocalDateTime created;
    private MetaEntity documents;
    private String externalCode;
    private GroupEntity group;
    private String id;
    private LocalDateTime moment;
    private String name;
    private AgentEntity organization;
    private EmployeeEntity owner;
    private Long payedSum;
    private ListEntity positions;
    private RateEntity rate;
    private Boolean shared;
    private StoreEntity store;
    private Long sum;
    private SupplyDocumentEntity supply;
    private LocalDateTime updated;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private Integer version;
}
