package com.lognex.api.model.entity.attribute;

import lombok.Getter;
import lombok.Setter;

public class Attribute<T> {

    public Attribute(String id, String name, String type,  AttributeValue<T> value){
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    @Getter
    private final String id;
    @Getter
    private final String name;
    @Getter
    private final String type;
    @Getter
    @Setter
    private AttributeValue<T> value;
}
