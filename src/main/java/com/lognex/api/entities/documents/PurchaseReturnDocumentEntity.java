package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.documents.markers.FinanceDocumentMarker;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PurchaseReturnDocumentEntity extends DocumentEntity implements IEntityWithAttributes {
    private AgentEntity agent;
    private LocalDateTime created;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private AgentEntity organization;
    private Long payedSum;
    private ListEntity<DocumentPosition> positions;
    private RateEntity rate;
    private StoreEntity store;
    private SupplyDocumentEntity supply;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private ContractEntity contract;
    private ProjectEntity project;
    private StateEntity state;
    private AccountEntity organizationAccount;
    private AccountEntity agentAccount;
    private List<AttributeEntity> attributes;
    private FactureOutDocumentEntity factureOut;
    private List<FinanceDocumentMarker> payments;

    public PurchaseReturnDocumentEntity(String id) {
        super(id);
    }
}
