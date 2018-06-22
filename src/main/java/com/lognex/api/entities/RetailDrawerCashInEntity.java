package com.lognex.api.entities;

import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.documents.DocumentEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetailDrawerCashInEntity extends DocumentEntity {
    private EmployeeEntity owner;
    private Boolean shared;
    private AgentEntity agent;
    private String externalCode;
    private MetaEntity documents;
    private String created;
    private Boolean applicable;
    private Integer sum;
    private Integer version;
    private String moment;
    private String accountId;
    private RateEntity rate;
    private String description;
    private OrganizationEntity organization;
    private String name;
    private String id;
    private String updated;
    private RetailShiftEntity retailShift;
    private GroupEntity group;
}
