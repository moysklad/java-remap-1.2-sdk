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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CashIn extends DocumentEntity implements FinanceInDocumentMarker, IEntityWithAttributes<DocumentAttribute>, HasFiles {
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
    private List<DocumentAttribute> attributes;
    private List<MetaEntity> operations;
    private FactureOut factureOut;
    private ListEntity<AttachedFile> files;

    public CashIn(String id) {
        super(id);
    }
}
