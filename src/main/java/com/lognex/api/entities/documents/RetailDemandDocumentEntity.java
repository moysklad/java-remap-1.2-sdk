package com.lognex.api.entities.documents;

import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.RetailShiftEntity;
import com.lognex.api.entities.RetailStoreEntity;
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
public class RetailDemandDocumentEntity extends DocumentEntity {
    private AgentEntity agent;
    private Long cashSum;
    private LocalDateTime created;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private Boolean fiscal;
    private Long noCashSum;
    private OrganizationEntity organization;
    private Integer payedSum;
    private ListEntity<DocumentPosition> positions;
    private RateEntity rate;
    private RetailShiftEntity retailShift;
    private RetailStoreEntity retailStore;
    private StoreEntity store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
}
