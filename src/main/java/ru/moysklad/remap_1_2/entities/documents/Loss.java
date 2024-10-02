package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.documents.positions.LossDocumentPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Loss extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    private Agent agent;
    private List<DocumentAttribute> attributes;
    private LocalDateTime created;
    private Contract contract;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Agent organization;
    private ListEntity<LossDocumentPosition> positions;
    private Project project;
    private Rate rate;
    private State state;
    private Store store;
    private String syncId;
    private SalesReturn salesReturn;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private ListEntity<AttachedFile> files;

    public Loss(String id) {
        super(id);
    }
}
