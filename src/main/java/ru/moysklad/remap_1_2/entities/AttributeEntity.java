package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Дополнительное поле
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AttributeEntity extends Attribute {

    public AttributeEntity(String id) {
        super(id);
    }

    public AttributeEntity(Meta.Type AttributeType, String id, Type type, Object value){
        super(AttributeType, id, type, value);
    }

    public AttributeEntity(Meta.Type AttributeType, String id, MetaEntity value){
        super(AttributeType, id, value);
    }
}
