package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityLegendable;

public class Project extends AbstractEntityLegendable {
    private boolean archived;
    //attributes

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
