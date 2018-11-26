package com.lognex.api.entities.documents;

import com.lognex.api.entities.AttributeEntity;
import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.RetailShiftEntity;
import com.lognex.api.entities.StateEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
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
public class RetailDrawerCashOutEntity extends DocumentEntity {
    private AgentEntity agent;
    private LocalDateTime created;
    private String description;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private OrganizationEntity organization;
    private RateEntity rate;
    private RetailShiftEntity retailShift;
    private String syncId;
    private LocalDateTime deleted;
    private StateEntity state;
    private List<AttributeEntity> attributes;
}
