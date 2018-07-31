package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CashOutDocumentEntity extends DocumentEntity {
    private AgentEntity agent;
    private Boolean applicable;
    private LocalDateTime created;
    private MetaEntity documents;
    private ExpenseItemEntity expenseItem;
    private String externalCode;
    private LocalDateTime moment;
    private OrganizationEntity organization;
    private ProjectEntity project;
    private RateEntity rate;
    private StateEntity state;
    private Long sum;
    private Integer vatSum;
}
