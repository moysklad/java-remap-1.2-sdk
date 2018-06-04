package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Юридическое Лицо
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class Organization extends Agent {
    private String id;
    private String accountId;
    private MetaEntity owner;
    private Boolean shared;
    private MetaEntity group;
    private Integer version;
    private LocalDateTime updated;
    private String name;
    private String externalCode;
    private Boolean archived;
    private LocalDateTime created;
    private CompanyType companyType;
    private String legalTitle;
    private String email;
    private MetaEntity accounts;
    private Boolean isEgaisEnable;
    private Boolean payerVat;
    private String director;
    private String chiefAccountant;
}
