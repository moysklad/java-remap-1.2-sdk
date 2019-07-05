package com.lognex.api.entities;

import com.lognex.api.entities.agents.EmployeeEntity;
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
public class ProjectEntity extends MetaEntity {
    private EmployeeEntity owner;
    private Boolean shared;
    private String code;
    private String externalCode;
    private String description;
    private Boolean archived;
    private LocalDateTime updated;
    private GroupEntity group;

    private List<AttributeEntity> attributes;

    public ProjectEntity(String id) {
        super(id);
    }
}
