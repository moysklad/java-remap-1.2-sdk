package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.ID;

public class Group extends AbstractEntity {
    private String name;

    public Group() {}

    public Group(ID id) {
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
