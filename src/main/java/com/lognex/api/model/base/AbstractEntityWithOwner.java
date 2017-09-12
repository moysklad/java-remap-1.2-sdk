package com.lognex.api.model.base;

import com.lognex.api.model.entity.Employee;
import com.lognex.api.model.entity.Group;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractEntityWithOwner extends AbstractEntity{
    private Employee owner;
    private Group group;
    private boolean shared;
}
