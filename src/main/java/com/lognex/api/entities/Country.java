package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Страна
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Country extends MetaEntity {
    /**
     * Внешний код
     */
    private String externalCode;

    /**
     * Момент последнего обновления сущности
     */
    private LocalDateTime updated;

    /**
     * Описание
     */
    private String description;

    /**
     * Код
     */
    private String code;

    public Country(String id) {
        super(id);
    }
}
