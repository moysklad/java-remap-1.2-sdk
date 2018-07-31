package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
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
public class CustomerOrderDocumentEntity extends DocumentEntity {
    private String accountId;
    private AgentEntity agent;
    private Boolean applicable;
    private ContractEntity contract;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private MetaEntity documents;
    private String externalCode;
    private GroupEntity group;
    private String id;
    private Long invoicedSum;
    private LocalDateTime moment;
    private String name;
    private OrganizationEntity organization;
    private EmployeeEntity owner;
    private Long payedSum;
    private ListEntity positions;
    private RateEntity rate;
    private Long reservedSum;
    private Boolean shared;
    private Long shippedSum;
    private StateEntity state;
    private MetaEntity store;
    private Long sum;
    private String syncId;
    private LocalDateTime updated;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private Integer version;
}
