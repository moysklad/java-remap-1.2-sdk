package com.lognex.api.model.entity.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeValue<T> {

    public AttributeValue(T value){
        this.value = value;
    }
    private T value;
}
