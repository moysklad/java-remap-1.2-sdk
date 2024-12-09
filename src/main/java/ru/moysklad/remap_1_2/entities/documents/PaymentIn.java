package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceInDocumentMarker;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentIn extends DocumentEntity implements FinanceInDocumentMarker, IEntityWithAttributes<DocumentAttribute>, HasFiles {
    private Agent agent;
    private LocalDateTime created;
    private String externalCode;
    private Organization organization;
    private Project project;
    private Rate rate;
    private State state;
    private Long vatSum;
    private Contract contract;
    private String paymentPurpose;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private List<DocumentAttribute> attributes;
    private LocalDateTime incomingDate;
    private String incomingNumber;
    private FactureOut factureOut;
    private List<MetaEntity> operations;
    private ListEntity<AttachedFile> files;

    public PaymentIn(String id) {
        super(id);
    }

    public void setLinkedOperations(List<LinkedOperation> operations) {
        if (operations != null) {
            this.operations = new ArrayList<>();
            this.operations.addAll(operations);
        }
    }

}
