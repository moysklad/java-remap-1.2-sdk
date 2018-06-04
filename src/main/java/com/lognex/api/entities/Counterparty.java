package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Контрагент
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class Counterparty extends Agent {
    private String id;
    private String accountId;
    private MetaEntity owner;
    private Boolean shared;
    private MetaEntity group;
    private Integer version;
    private LocalDateTime updated;
    private String name;
    private String code;
    private String externalCode;
    private Boolean archived;
    private LocalDateTime created;
    private CompanyType companyType;
    private String legalTitle;
    private String legalAddress;
    private String inn;
    private String email;
    private String phone;
    private String fax;
    private MetaEntity accounts;
    private List<String> tags;
    private List<Discount> discounts;
    private MetaEntity contactpersons;
    private MetaEntity notes;
    private String discountCardNumber;
    private State state;
    private Integer salesAmount;

    public static class Discount {
        private MetaEntity discount;
        private Integer personalDiscount;
    }
}
