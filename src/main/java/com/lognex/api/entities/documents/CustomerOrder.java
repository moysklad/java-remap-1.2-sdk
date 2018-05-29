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
@EqualsAndHashCode
public class CustomerOrder extends MetaEntity {
    public String id;
    public String accountId;
    public MetaEntity owner;
    public Boolean shared;
    public MetaEntity group;
    public Integer version;
    public String updated;
    public String name;
    public String externalCode;
    public String moment;
    public Boolean applicable;
    public Rate rate;
    public Integer sum;
    public MetaEntity store;
    public Counterparty agent;
    public Organization organization;
    public State state;
    public MetaEntity documents;
    public String created;
    public MetaEntity positions;
    public Boolean vatEnabled;
    public Boolean vatIncluded;
    public Integer vatSum;
    public Integer payedSum;
    public Integer shippedSum;
    public Integer invoicedSum;
    public Integer reservedSum;
}
