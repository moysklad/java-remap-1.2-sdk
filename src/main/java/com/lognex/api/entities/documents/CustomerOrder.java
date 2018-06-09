package com.lognex.api.entities.documents;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.StateEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Заказ Покупателя
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerOrder extends DocumentEntity {
    private String id;
    private String accountId;
    private String syncId;
    private Integer version;
    private LocalDateTime updated;
    private LocalDateTime deleted;
    private String name;
    private String description;
    private String externalCode;
    private LocalDateTime moment;
    private Boolean applicable;

    private EmployeeEntity owner;
    private Boolean shared;
    private MetaEntity group;
    private RateEntity rate;
    private Integer sum;
    private MetaEntity store;
    private AgentEntity agent;
    private OrganizationEntity organization;
    private StateEntity state;
    private MetaEntity documents;
    private LocalDateTime created;
    private MetaEntity positions;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Integer vatSum;
    private Integer payedSum;
    private Integer shippedSum;
    private Integer invoicedSum;
    private Integer reservedSum;
}
