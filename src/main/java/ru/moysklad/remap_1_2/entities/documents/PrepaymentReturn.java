package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.positions.PrepaymentDocumentPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PrepaymentReturn extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Rate rate;
    private Agent agent;
    private Organization organization;
    private State state;
    private List<DocumentAttribute> attributes;
    private LocalDateTime created;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private RetailStore retailStore;
    private RetailShift retailShift;
    private Prepayment prepayment;
    private Long cashSum;
    private Long noCashSum;
    private TaxSystem taxSystem;
    private ListEntity<PrepaymentDocumentPosition> positions;
    private Long qrSum;
    private ListEntity<AttachedFile> files;

    public PrepaymentReturn(String id) {
        super(id);
    }
}
