package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.markers.FinanceDocumentMarker;
import com.lognex.api.entities.documents.positions.CommissionReportDocumentPosition;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommissionReportIn extends DocumentEntity implements IEntityWithAttributes {
    private Agent agent;
    private LocalDateTime commissionPeriodEnd;
    private LocalDateTime commissionPeriodStart;
    private Long commitentSum;
    private Contract contract;
    private LocalDateTime created;
    private String externalCode;
    private Organization organization;
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
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private List<Attribute> attributes;
    private List<FinanceDocumentMarker> payments;

    public CommissionReportIn(String id) {
        super(id);
    }
}
