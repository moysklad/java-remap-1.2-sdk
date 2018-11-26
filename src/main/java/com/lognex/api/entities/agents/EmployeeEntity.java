package com.lognex.api.entities.agents;

import com.lognex.api.entities.AttributeEntity;
import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.ImageEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime created;
    private String fullName;
    private Boolean archived;
    private String uid;
    private EmployeeEntity cashier;
    private String updated;
    private String email;
    private GroupEntity group;
    private String description;
    private String phone;
    private String firstName;
    private String middleName;
    private List<AttributeEntity> attributes;
    private ImageEntity image;
    private String inn;
    private String position;
}
