package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
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
public class PurchaseOrderDocumentEntity extends DocumentEntity {
    private AgentEntity agent;
    private Boolean applicable;
    private ContractEntity contract;
    private LocalDateTime created;
    private String description;
    private MetaEntity documents;
    private String externalCode;
    private Long invoicedSum;
    private LocalDateTime moment;
    private OrganizationEntity organization;
    private Long payedSum;
    private ListEntity positions;
    private RateEntity rate;
    private Long shippedSum;
    private StateEntity state;
    private StoreEntity store;
    private Long sum;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private Long waitSum;
}
