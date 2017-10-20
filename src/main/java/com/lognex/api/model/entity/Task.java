package com.lognex.api.model.entity;

import com.lognex.api.model.base.EntityInfoable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Task extends EntityInfoable {

    private Employee author;
    private String description;
    private Date dueToDate;
    private Employee assignee;
    private boolean done;
    private Agent agent;
}
