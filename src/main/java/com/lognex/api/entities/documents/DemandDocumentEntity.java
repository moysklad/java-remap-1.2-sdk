package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.documents.markers.FinanceDocumentMarker;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Отгрузка
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DemandDocumentEntity extends DocumentEntity {
    private AgentEntity agent;
    private AccountEntity agentAccount;
    private List<AttributeEntity> attributes;
    private ContractEntity contract;
    private LocalDateTime created;
    private CustomerOrderDocumentEntity customerOrder;
    private LocalDateTime deleted;
    private String description;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private OrganizationEntity organization;
    private AccountEntity organizationAccount;
    private Overhead overhead;
    private Long payedSum;
    private ListEntity<DocumentPosition> positions;
    private ProjectEntity project;
    private RateEntity rate;
    private StateEntity state;
    private StoreEntity store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private FactureOutDocumentEntity factureOut;
    private ListEntity<OperationEntity> returns;
    private List<FinanceDocumentMarker> payments;
    private List<InvoiceOutDocumentEntity> invoicesOut;
    private AgentEntity consignee;
    private String transportFacilityNumber;
    private String shippingInstructions;
    private String cargoName;
    private String transportFacility;
    private Integer goodPackQuantity;
    private AgentEntity carrier;
    private String stateContractId;
}
