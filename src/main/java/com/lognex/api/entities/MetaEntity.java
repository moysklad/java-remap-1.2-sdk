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
@EqualsAndHashCode
public class MetaEntity extends Entity {
    public Meta meta;

    public MetaEntity(Meta meta) {
        this.meta = meta;
    }
}
