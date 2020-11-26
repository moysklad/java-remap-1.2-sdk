package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.positions.RetailSalesDocumentPosition;
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
public class RetailDemand extends DocumentEntity implements IEntityWithAttributes {
    private Agent agent;
    private Long cashSum;
    private LocalDateTime created;
    private String externalCode;
    private Boolean fiscal;
    private Long noCashSum;
    private Organization organization;
    private Long payedSum;
    private ListEntity<RetailSalesDocumentPosition> positions;
    private Rate rate;
    private RetailShift retailShift;
    private RetailStore retailStore;
    private Store store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private LocalDateTime deleted;
    private String description;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private List<Attribute> attributes;
    private String fiscalPrinterInfo;
    private String documentNumber;
    private String checkNumber;
    private Long checkSum;
    private String sessionNumber;
    private String ofdCode;
    private CustomerOrder customerOrder;
    private Contract contract;
    private Long prepaymentCashSum;
    private Long prepaymentNoCashSum;
    private Project project;
    private State state;
    private TaxSystem taxSystem;
    private Long qrSum;
    private Long prepaymentQrSum;

    public RetailDemand(String id) {
        super(id);
    }
}
