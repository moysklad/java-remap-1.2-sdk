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

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InvoiceIn extends DocumentEntity implements HasFiles {
    private Agent agent;
    private AgentAccount agentAccount;
    private Contract contract;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private String incomingNumber;
    private LocalDateTime incomingDate;
    private Organization organization;
    private AgentAccount organizationAccount;
    private Long payedSum;
    private LocalDateTime paymentPlannedMoment;
    private ListEntity<InvoiceDocumentPosition> positions;
    private Rate rate;
    private Long shippedSum;
    private State state;
    private Store store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private Project project;
    private ListEntity<AttachedFile> files;

    public InvoiceIn(String id) {
        super(id);
    }
}
