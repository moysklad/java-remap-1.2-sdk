package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность, имеющая поле Метаданных
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MetaEntity extends Entity {
    private String id;
    private Integer version;
    private String accountId;
    private String name;

    protected Meta meta;
}
