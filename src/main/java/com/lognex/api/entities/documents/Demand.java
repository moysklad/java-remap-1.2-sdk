package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Отгрузка
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Demand extends MetaEntity {
    public String id;
    public String accountId;
    public String syncId;
    public Integer version;
    public LocalDateTime updated;
    public LocalDateTime deleted;
    public LocalDateTime created;
    public String name;
    public String description;
    public String externalCode;
    public LocalDateTime moment;
    public Boolean applicable;
    public Boolean vatEnabled;
    public Boolean vatIncluded;
    public Integer sum;
    public Rate rate;
    public Employee owner;
    public Boolean shared;
    public MetaEntity group;
    public Organization organization;
    public Agent agent;
    public Store store;
    public Contract contract;
    public MetaEntity project;
    public State state;
    public MetaEntity organizationAccount;
    public MetaEntity agentAccount;
    public MetaEntity attributes;
    public MetaEntity documents;
    public MetaEntity positions;
    public Integer vatSum;
    public Integer payedSum;
}
