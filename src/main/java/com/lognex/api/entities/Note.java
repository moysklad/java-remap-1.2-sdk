package com.lognex.api.entities;

import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Employee;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
