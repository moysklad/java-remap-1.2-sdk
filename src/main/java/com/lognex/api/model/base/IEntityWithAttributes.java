package com.lognex.api.model.base;

import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.util.ID;

import java.util.Set;

public interface IEntityWithAttributes {
    Set<Attribute<?>> getAttributes();
    Attribute<?> getAttribute(String attributeId);
}
