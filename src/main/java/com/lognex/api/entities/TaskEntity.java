package com.lognex.api.entities;

import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskEntity extends MetaEntity implements Fetchable {
    private EmployeeEntity author;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String description;
    private LocalDateTime dueToDate;
    private EmployeeEntity assignee;
    private Boolean done;
    private AgentEntity agent;
}
