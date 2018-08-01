package com.lognex.api.entities.documents;

import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.RetailShiftEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetailDrawerCashOutEntity extends DocumentEntity {
    private AgentEntity agent;
    private String created;
    private String description;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private OrganizationEntity organization;
    private RateEntity rate;
    private RetailShiftEntity retailShift;
}
