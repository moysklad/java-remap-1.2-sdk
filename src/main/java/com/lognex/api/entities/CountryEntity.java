package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CountryEntity extends MetaEntity {
    /**
     * ID в формате UUID
     */
    private String id;

    /**
     * Наименование Страны
     */
    private String name;

    /**
     * ID учетной записи
     */
    private String accountId;

    /**
     * Внешний код Страны
     */
    private String externalCode;

    /**
     * Версия сущности
     */
    private Integer version;

    /**
     * Момент последнего обновления сущности
     */
    private LocalDateTime updated;
}
