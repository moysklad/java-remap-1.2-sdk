package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.documents.positions.MoveDocumentPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Move extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    private List<DocumentAttribute> attributes;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Agent organization;
    private Overhead overhead;
    private ListEntity<MoveDocumentPosition> positions;
    private Project project;
    private Rate rate;
    private Store sourceStore;
    private State state;
    private String syncId;
    private Store targetStore;
    private InternalOrder internalOrder;
    private ListEntity<AttachedFile> files;

    public Move(String id) {
        super(id);
    }
}
