package com.lognex.api.model.base;

import com.lognex.api.model.entity.attribute.Attribute;

import java.util.Optional;
import java.util.Set;

public interface IEntityWithAttributes {
    Set<Attribute<?>> getAttributes();
    default Optional<Attribute<?>> getAttribute(String attributeId) {
        return getAttributes().stream().filter(a -> a.getId().equals(attributeId)).findFirst();
    }
}
