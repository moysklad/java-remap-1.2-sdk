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
public class RetailSalesReturnEntity extends DocumentEntity {
    private AgentEntity agent;
    private Boolean applicable;
    private Integer cashSum;
    private String created;
    private RetailDemandDocumentEntity demand;
    private MetaEntity documents;
    private String externalCode;
    private LocalDateTime moment;
    private Integer noCashSum;
    private AgentEntity organization;
    private ListEntity positions;
    private RateEntity rate;
    private RetailShiftEntity retailShift;
    private RetailStoreEntity retailStore;
    private StoreEntity store;
    private Long sum;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Integer vatSum;
}
