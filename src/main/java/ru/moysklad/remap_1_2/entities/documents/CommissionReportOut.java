package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceDocumentMarker;
import ru.moysklad.remap_1_2.entities.documents.positions.CommissionReportDocumentPosition;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommissionReportOut extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    private Agent agent;
    private LocalDateTime commissionPeriodEnd;
    private LocalDateTime commissionPeriodStart;
    private Long commitentSum;
    private Contract contract;
    private LocalDateTime created;
    private String externalCode;
    private Organization organization;
    private AgentAccount organizationAccount;
    private Long payedSum;
    private ListEntity<CommissionReportDocumentPosition> positions;
    private Rate rate;
    private Double rewardPercent;
    private RewardType rewardType;
    private State state;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private Project project;
    private AgentAccount agentAccount;
    private List<DocumentAttribute> attributes;
    private List<FinanceDocumentMarker> payments;
    private ListEntity<AttachedFile> files;

    public CommissionReportOut(String id) {
        super(id);
    }
}
