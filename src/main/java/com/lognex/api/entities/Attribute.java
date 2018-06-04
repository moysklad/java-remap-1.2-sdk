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
    private String id;
    private String name;
    private AttributeType type;
    private String value;
    private Boolean required;

    public enum AttributeType {
        string
    }
}
