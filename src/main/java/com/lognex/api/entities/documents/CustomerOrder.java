package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Заказ Покупателя
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerOrder extends MetaEntity {
    private String id;
    private String accountId;
    private MetaEntity owner;
    private Boolean shared;
    private MetaEntity group;
    private Integer version;
    private String updated;
    private String name;
    private String externalCode;
    private String moment;
    private Boolean applicable;
    private Rate rate;
    private Integer sum;
    private MetaEntity store;
    private Agent agent;
    private Organization organization;
    private State state;
    private MetaEntity documents;
    private String created;
    private MetaEntity positions;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Integer vatSum;
    private Integer payedSum;
    private Integer shippedSum;
    private Integer invoicedSum;
    private Integer reservedSum;
}
