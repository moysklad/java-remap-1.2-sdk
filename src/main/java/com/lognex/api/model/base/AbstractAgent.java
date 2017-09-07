package com.lognex.api.model.base;

public abstract class AbstractAgent extends AbstractEntityLegendable {
    private boolean archived;

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
