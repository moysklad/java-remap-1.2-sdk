package com.lognex.api.model.base;

import com.lognex.api.util.ID;

import java.util.Date;

public abstract class AbstractEntityInfoable extends AbstractEntityWithOwner {
    private long version;
    private Date updated;
    private ID updatedBy;
    private Date created;
    private Date deleted;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public ID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(ID updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }
}
