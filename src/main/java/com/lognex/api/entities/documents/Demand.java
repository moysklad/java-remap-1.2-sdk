package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.markers.FinanceDocumentMarker;
import com.lognex.api.entities.documents.positions.DemandDocumentPosition;
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
public class Demand extends DocumentEntity implements IEntityWithAttributes {
    private Agent agent;
    private AgentAccount agentAccount;
    private List<Attribute> attributes;
    private Contract contract;
    private LocalDateTime created;
    private CustomerOrder customerOrder;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Organization organization;
    private AgentAccount organizationAccount;
    private Overhead overhead;
    private Long payedSum;
    private ListEntity<DemandDocumentPosition> positions;
    private Project project;
    private Rate rate;
    private State state;
    private Store store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private FactureOut factureOut;
    private List<SalesReturn> returns;
    private List<FinanceDocumentMarker> payments;
    private List<InvoiceOut> invoicesOut;
    private Agent consignee;
    private String transportFacilityNumber;
    private String shippingInstructions;
    private String cargoName;
    private String transportFacility;
    private Integer goodPackQuantity;
    private Agent carrier;
    private String stateContractId;

    public Demand(String id) {
        super(id);
    }
}
