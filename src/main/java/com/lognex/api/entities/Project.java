package com.lognex.api.entities;

import com.lognex.api.entities.agents.Employee;
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
public class Project extends MetaEntity {
    private Employee owner;
    private Boolean shared;
    private String code;
    private String externalCode;
    private String description;
    private Boolean archived;
    private LocalDateTime updated;
    private Group group;

    private List<Attribute> attributes;

    public Project(String id) {
        super(id);
    }
}
