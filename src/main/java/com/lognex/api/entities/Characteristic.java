package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Характеристика Модификации
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Characteristic extends MetaEntity {
    public String id;
    public String name;
    public String type;
    public Boolean required;
    public String value;
}
