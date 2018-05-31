package com.lognex.api.entities;

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
public class Attribute extends MetaEntity {
    public String id;
    public String name;
    public AttributeType type;
    public String value;
    public Boolean required;

    public enum AttributeType {
        string
    }
}
