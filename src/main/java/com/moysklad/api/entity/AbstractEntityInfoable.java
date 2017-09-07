package com.moysklad.api.entity;

import com.moysklad.api.util.ID;

import java.util.Date;

public abstract class AbstractEntityInfoable extends AbstractEntityWithOwner {
    private long version;
    private Date updated;
    private ID updatedBy;
    private Date created;
    private Date deleted;

}
