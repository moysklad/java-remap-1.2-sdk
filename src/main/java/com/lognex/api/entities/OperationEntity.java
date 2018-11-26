package com.lognex.api.entities;

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
public class OperationEntity extends MetaEntity {
    private EmployeeEntity owner;
    private GroupEntity group;
    private Boolean shared;
    private String description;
    private String code;
    private String externalCode;
    private LocalDateTime updated;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private LocalDateTime moment;
    private Boolean applicable;
    private Double sum;
    private ContractEntity contract;
    private ProjectEntity project;
    private StateEntity state;
    private String syncId;
    private CurrencyEntity rate;
    private OrganizationEntity organization;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private AgentEntity agent;
}
