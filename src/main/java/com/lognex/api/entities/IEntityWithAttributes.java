package com.lognex.api.entities;

import java.util.List;
import java.util.Optional;

public interface IEntityWithAttributes {
    List<Attribute> getAttributes();

    default Optional<Attribute> getAttribute(String attributeId) {
        if (getAttributes() == null){
            return Optional.empty();
        }
        return getAttributes().stream().filter(a -> a.getId().equals(attributeId)).findFirst();
    }
}
