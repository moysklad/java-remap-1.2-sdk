package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.documents.positions.RetailSalesDocumentPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetailSalesReturn extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    private Agent agent;
    private Long cashSum;
    private LocalDateTime created;
    private RetailDemand demand;
    private String externalCode;
    private Long noCashSum;
    private Agent organization;
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
    private Project project;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private List<DocumentAttribute> attributes;
    private Contract contract;
    private State state;
    private TaxSystem taxSystem;
    private Long qrSum;
    private ListEntity<AttachedFile> files;

    public RetailSalesReturn(String id) {
        super(id);
    }
}
