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
    private String id;
    private String accountId;
    private String syncId;
    private Integer version;
    private LocalDateTime updated;
    private LocalDateTime deleted;
    private LocalDateTime created;
    private String name;
    private String description;
    private String externalCode;
    private LocalDateTime moment;
    private Boolean applicable;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Integer sum;
    private Rate rate;
    private Employee owner;
    private Boolean shared;
    private MetaEntity group;
    private Organization organization;
    private Agent agent;
    private Store store;
    private Contract contract;
    private MetaEntity project;
    private State state;
    private MetaEntity organizationAccount;
    private MetaEntity agentAccount;
    private MetaEntity attributes;
    private MetaEntity documents;
    private MetaEntity positions;
    private Integer vatSum;
    private Integer payedSum;
}
