package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Employee;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Note extends MetaEntity {
    private LocalDateTime created;
    private String description;
    private Agent agent;
    private Employee author;

    public Note(String id) {
        super(id);
    }
}
