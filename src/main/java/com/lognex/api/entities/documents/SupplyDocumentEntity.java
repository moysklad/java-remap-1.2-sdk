package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
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
public class SupplyDocumentEntity extends DocumentEntity {
    private String accountId;
    private AgentEntity agent;
    private Boolean applicable;
    private ContractEntity contract;
    private LocalDateTime created;
    private MetaEntity documents;
    private String externalCode;
    private GroupEntity group;
    private String id;
    private LocalDateTime moment;
    private String name;
    private OrganizationEntity organization;
    private Overhead overhead;
    private EmployeeEntity owner;
    private Integer payedSum;
    private ListEntity positions;
    private PurchaseOrderDocumentEntity purchaseOrder;
    private RateEntity rate;
    private Boolean shared;
    private StoreEntity store;
    private Long sum;
    private LocalDateTime updated;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Integer vatSum;
    private Integer version;
}
