package com.lognex.api.entities.documents;

import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CashInDocumentEntity extends DocumentEntity {
    private EmployeeEntity owner;
    private Boolean shared;
    private AgentEntity agent;
    private String externalCode;
    private MetaEntity documents;
    private String created;
    private Boolean applicable;
    private Long sum;
    private Integer vatSum;
    private Integer version;
    private String moment;
    private String accountId;
    private List<MetaEntity> operations;
    private RateEntity rate;
    private OrganizationEntity organization;
    private String name;
    private String id;
    private String paymentPurpose;
    private String updated;
    private GroupEntity group;
}