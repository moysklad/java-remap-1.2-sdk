package com.lognex.api.entities.documents;

import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
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
public class SalesReturnDocumentEntity extends DocumentEntity {
    private AgentEntity agent;
    private LocalDateTime created;
    private DemandDocumentEntity demand;
    private String description;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private OrganizationEntity organization;
    private Long payedSum;
    private ListEntity positions;
    private RateEntity rate;
    private StoreEntity store;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
}
