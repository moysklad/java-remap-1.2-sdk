package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetailDrawerCashIn extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    private Agent agent;
    private LocalDateTime created;
    private String description;
    private String externalCode;
    private Organization organization;
    private Rate rate;
    private RetailShift retailShift;
    private String syncId;
    private LocalDateTime deleted;
    private State state;
    private List<DocumentAttribute> attributes;
    private ListEntity<AttachedFile> files;

    public RetailDrawerCashIn(String id) {
        super(id);
    }
}
