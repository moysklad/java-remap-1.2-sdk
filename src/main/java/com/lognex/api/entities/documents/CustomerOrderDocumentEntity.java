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
    private String id;
    private String accountId;
    private String syncId;
    private Integer version;
    private LocalDateTime updated;
    private LocalDateTime deleted;
    private LocalDateTime created;
    private String name;
    private String description;
    private String externalCode;
    private LocalDateTime moment;
    private Boolean applicable;
    private ListEntity<DocumentPosition> positions;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private ContractEntity contract;
    private EmployeeEntity owner;
    private Boolean shared;
    private GroupEntity group;
    private Long vatSum;
    private Long payedSum;
    private OrganizationEntity organization;
    private Long sum;
    private RateEntity rate;
    private AgentEntity agent;
    private MetaEntity store;
    private MetaEntity documents;
    private StateEntity state;

    private Long shippedSum;
    private Long invoicedSum;
    private Long reservedSum;
}
