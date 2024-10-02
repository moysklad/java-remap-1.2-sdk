package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.positions.InvoiceDocumentPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InvoiceOut extends DocumentEntity implements HasFiles {
    private Agent agent;
    private AgentAccount agentAccount;
    private List<DocumentAttribute> attributes;
    private Contract contract;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Organization organization;
    private AgentAccount organizationAccount;
    private Long payedSum;
    private LocalDateTime paymentPlannedMoment;
    private ListEntity<InvoiceDocumentPosition> positions;
    private Project project;
    private Rate rate;
    private Long shippedSum;
    private State state;
    private Store store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private ListEntity<AttachedFile> files;

    public InvoiceOut(String id) {
        super(id);
    }
}
