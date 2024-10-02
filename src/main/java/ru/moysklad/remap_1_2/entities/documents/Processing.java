package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Processing extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    private LocalDateTime created;
    private String externalCode;
    private ListEntity<DocumentPosition> materials;
    private Store materialsStore;
    private Organization organization;
    private ProcessingPlan processingPlan;
    private Long processingSum;
    private ListEntity<DocumentPosition> products;
    private Store productsStore;
    private Double quantity;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private AgentAccount organizationAccount;
    private State state;
    private List<DocumentAttribute> attributes;
    private Project project;
    private ProcessingOrder processingOrder;
    private ListEntity<AttachedFile> files;

    public Processing(String id) {
        super(id);
    }
}
