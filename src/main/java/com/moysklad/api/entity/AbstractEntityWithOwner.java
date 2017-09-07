package com.moysklad.api.entity;

import com.moysklad.api.util.ID;

public abstract class AbstractEntityWithOwner extends AbstractEntity{
    private ID ownerId;
    private ID groupId;
    private boolean shared;
}
