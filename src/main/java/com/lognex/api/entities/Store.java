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
    private String id;
    private String accountId;
    private MetaEntity owner;
    private Boolean shared;
    private MetaEntity group;
    private Integer version;
    private String updated;
    private String name;
    private String description;
    private String code;
    private String externalCode;
    private Boolean archived;
    private String address;
    private MetaEntity parent;
    private String pathName;
    private MetaEntity attributes;
}
