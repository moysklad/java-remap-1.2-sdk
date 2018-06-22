package com.lognex.api.entities.agents;

import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class EmployeeEntity extends AgentEntity {
    private EmployeeEntity owner;
    private Boolean shared;
    private String lastName;
    private String externalCode;
    private String shortFio;
    private String created;
    private String fullName;
    private Integer version;
    private String accountId;
    private Boolean archived;
    private Boolean uid;
    private MetaEntity cashier;
    private String name;
    private String id;
    private String updated;
    private String email;
    private GroupEntity group;
}
