package ru.moysklad.remap_1_2.entities;

import java.util.List;
import java.util.Optional;

public interface IEntityOperationWithAttributes {
    List<AttributeOperation> getAttributes();

    default Optional<AttributeOperation> getAttribute(String attributeId) {
        if (getAttributes() == null){
            return Optional.empty();
        }
        return getAttributes().stream().filter(a -> a.getId().equals(attributeId)).findFirst();
    }
}
