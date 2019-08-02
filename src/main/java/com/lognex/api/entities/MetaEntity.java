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
    public MetaEntity(String id) {
        this.id = id;
    }

    private String id;
    private String accountId;
    private String name;

    protected Meta meta;
}
