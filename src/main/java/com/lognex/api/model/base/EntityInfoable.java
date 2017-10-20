package com.lognex.api.model.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class EntityInfoable extends EntityWithOwner {
    private long version;
    private Date updated;
    private Date created;
    private Date deleted;
}
