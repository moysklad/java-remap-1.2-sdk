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
    private String id;
    private String name;
    private String type;
    private Boolean required;
    private String value;
}
