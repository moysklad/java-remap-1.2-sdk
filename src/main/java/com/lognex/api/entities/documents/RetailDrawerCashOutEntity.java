package com.lognex.api.entities.documents;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.RetailShiftEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
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
    private Boolean applicable;
    private String created;
    private String description;
    private MetaEntity documents;
    private String externalCode;
    private String moment;
    private OrganizationEntity organization;
    private RateEntity rate;
    private RetailShiftEntity retailShift;
    private Long sum;
}
