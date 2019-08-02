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
public class Task extends MetaEntity implements Fetchable {
    private Employee author;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String description;
    private LocalDateTime dueToDate;
    private Employee assignee;
    private Boolean done;
    private Agent agent;

    public Task(String id) {
        super(id);
    }
}
