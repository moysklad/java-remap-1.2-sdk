package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Контрагент
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class Counterparty extends MetaEntity {
    public String id;
    public String accountId;
    public MetaEntity owner;
    public Boolean shared;
    public MetaEntity group;
    public Integer version;
    public String updated;  // TODO Сделать датой
    public String name;
    public String code;
    public String externalCode;
    public Boolean archived;
    public String created;  // TODO Сделать датой
    public String companyType;  // TODO Сделать enum
    public String legalTitle;
    public String legalAddress;
    public String inn;
    public String email;
    public String phone;
    public String fax;
    public MetaEntity accounts;
    public List<String> tags;
    public List<Discount> discounts;
    public MetaEntity contactpersons;
    public MetaEntity notes;
    public String discountCardNumber;
    public State state;
    public Integer salesAmount;

    public static class Discount {
        public MetaEntity discount;
        public Integer personalDiscount;
    }
}
