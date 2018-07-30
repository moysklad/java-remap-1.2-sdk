package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
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
    private String id;
    private String accountId;
    private EmployeeEntity owner;
    private Boolean shared;
    private StateEntity state;
    private GroupEntity group;
    private Integer version;
    private LocalDateTime updated;
    private String name;
    private String externalCode;
    private LocalDateTime moment;
    private Boolean applicable;
    private RateEntity rate;
    private Long sum;
    private AgentEntity agent;
    private OrganizationEntity organization;
    private MetaEntity documents;
    private LocalDateTime created;
    private Integer vatSum;
    private ExpenseItemEntity expenseItem;
    private ProjectEntity project;
}
