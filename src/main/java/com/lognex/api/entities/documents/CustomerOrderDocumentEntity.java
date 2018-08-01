package com.lognex.api.entities.documents;

import com.lognex.api.entities.ContractEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.StateEntity;
import com.lognex.api.entities.agents.AgentEntity;
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
    private AgentEntity agent;
    private ContractEntity contract;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private MetaEntity documents;
    private String externalCode;
    private Long invoicedSum;
    private OrganizationEntity organization;
    private Long payedSum;
    private ListEntity positions;
    private RateEntity rate;
    private Long reservedSum;
    private Long shippedSum;
    private StateEntity state;
    private MetaEntity store;
    private String syncId;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
}
