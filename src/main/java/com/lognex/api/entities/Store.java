package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Store extends MetaEntity {
    public String id;
    public String accountId;
    public MetaEntity owner;
    public Boolean shared;
    public MetaEntity group;
    public Integer version;
    public String updated;
    public String name;
    public String description;
    public String code;
    public String externalCode;
    public Boolean archived;
    public String address;
    public MetaEntity parent;
    public String pathName;
    public MetaEntity attributes;
}
