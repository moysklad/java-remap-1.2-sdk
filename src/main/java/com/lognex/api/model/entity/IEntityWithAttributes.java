package com.lognex.api.model.entity;

import com.lognex.api.model.entity.attribute.Attribute;

import java.util.Set;

public interface IEntityWithAttributes {
    Set<Attribute> getAttributes();
}
