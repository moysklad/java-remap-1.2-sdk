package com.lognex.api.model.base;

import java.util.Date;

public abstract class AbstractEntityInfoable extends AbstractEntityWithOwner {
    private long version;
    private Date updated;
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
