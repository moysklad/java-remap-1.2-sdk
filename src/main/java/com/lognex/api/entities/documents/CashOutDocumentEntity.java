package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.documents.markers.FinanceOutDocumentMarker;
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
public class CashOutDocumentEntity extends DocumentEntity implements FinanceOutDocumentMarker {
    private AgentEntity agent;
    private LocalDateTime created;
    private ListEntity<DocumentEntity> documents;
    private ExpenseItemEntity expenseItem;
    private String externalCode;
    private OrganizationEntity organization;
    private ProjectEntity project;
    private RateEntity rate;
    private StateEntity state;
    private Long vatSum;
    private ContractEntity contract;
    private String paymentPurpose;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private List<AttributeEntity> attributes;
    private List<MetaEntity> operations;
    private FactureInDocumentEntity factureIn;
}
