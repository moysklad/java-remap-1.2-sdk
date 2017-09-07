package com.lognex.api.model.base;

import com.lognex.api.model.entity.Employee;
import com.lognex.api.model.entity.Group;

public abstract class AbstractEntityWithOwner extends AbstractEntity{
    private Employee owner;
    private Group group;
    private boolean shared;

    public Employee getOwner() {
        return owner;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
