package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Единица Измерения
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UomEntity extends MetaEntity {
    /**
     * Дата последнего обновления
     */
    private LocalDateTime update;

    /**
     * Описание
     */
    private String description;

    /**
     * Код
     */
    private String code;

    /**
     * Внешний код
     */
    private String externalCode;
}
