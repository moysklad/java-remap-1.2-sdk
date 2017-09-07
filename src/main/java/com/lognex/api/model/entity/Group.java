package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntity;

public class Group extends AbstractEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
