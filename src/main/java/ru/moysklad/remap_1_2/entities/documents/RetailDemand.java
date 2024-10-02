package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.positions.RetailSalesDocumentPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetailDemand extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
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
    private List<DocumentAttribute> attributes;
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
    private ListEntity<AttachedFile> files;

    public RetailDemand(String id) {
        super(id);
    }
}
