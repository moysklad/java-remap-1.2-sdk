package com.lognex.api.entities;

import java.util.List;
import java.util.Optional;

public interface IEntityWithAttributes {
    List<AttributeEntity> getAttributes();

    default Optional<AttributeEntity> getAttribute(String attributeId) {
        return getAttributes().stream().filter(a -> a.getId().equals(attributeId)).findFirst();
    }
}
