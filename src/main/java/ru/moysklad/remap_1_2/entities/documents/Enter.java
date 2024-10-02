package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.positions.EnterDocumentPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Оприходование
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Enter extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    private Contract contract;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Organization organization;
    private ListEntity<EnterDocumentPosition> positions;
    private Rate rate;
    private State state;
    private Store store;
    private String syncId;
    private Project project;
    private List<DocumentAttribute> attributes;
    private Overhead overhead;
    private ListEntity<AttachedFile> files;

    public Enter(String id) {
        super(id);
    }
}
