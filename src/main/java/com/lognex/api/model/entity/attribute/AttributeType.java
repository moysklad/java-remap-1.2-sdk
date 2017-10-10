package com.lognex.api.model.entity.attribute;

import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public enum AttributeType {
    STRING, TEXT, LONG, TIME, DOUBLE, LINK, FILE, //PRIMITIVE
        CUSTOMENTITY,
        COUNTERPARTY, PRODUCT, SERVICE, CONSIGNMENT, VARIANT, BUNDLE, STORE, PROJECT, CONTRACT, EMPLOYEE; // Embedded entity

    public static final Set<AttributeType> EMBEDDED_ENTITIES = ImmutableSet.of(COUNTERPARTY, PRODUCT, SERVICE, CONSIGNMENT, VARIANT, BUNDLE, STORE, PROJECT, CONTRACT, EMPLOYEE);

    public static AttributeType find(String typeName){
        Optional<AttributeType> type = Arrays.stream(values()).filter(t -> t.name().toLowerCase().equals(typeName)).findFirst();
        if (type.isPresent()){
            return type.get();
        }
        throw new IllegalArgumentException("No such attribute type for string=" + typeName);
    }
}
