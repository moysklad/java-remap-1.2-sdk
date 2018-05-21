package com.lognex.api.entities;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public final class Organization extends MetaEntity {
    public String id;
    public String accountId;
    public MetaEntity owner;
    public Boolean shared;
    public MetaEntity group;
    public Integer version;
    public String updated;
    public String name;
    public String externalCode;
    public Boolean archived;
    public String created;
    public String companyType;
    public String legalTitle;
    public String email;
    public MetaEntity accounts;
    public Boolean isEgaisEnable;
    public Boolean payerVat;
    public String director;
    public String chiefAccountant;

    public Organization(Meta meta) {
        super(meta);
    }
}
