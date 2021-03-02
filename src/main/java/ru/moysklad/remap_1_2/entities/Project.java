package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.agents.Employee;

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
