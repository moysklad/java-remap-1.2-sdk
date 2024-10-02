package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.documents.positions.ProcessingOrderPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessingOrder extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    private List<DocumentAttribute> attributes;
    private String description;
    private String externalCode;
    private Agent organization;
    private ListEntity<ProcessingOrderPosition> positions;
    private ProcessingPlan processingPlan;
    private Double quantity;
    private State state;
    private Store store;
    private String syncId;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private AgentAccount organizationAccount;
    private LocalDateTime deliveryPlannedMoment;
    private Project project;
    private List<Processing> processings;
    private ListEntity<AttachedFile> files;

    public ProcessingOrder(String id) {
        super(id);
    }
}
