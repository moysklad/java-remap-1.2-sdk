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
     * Внешний код Страны
     */
    private String externalCode;

    /**
     * Момент последнего обновления сущности
     */
    private LocalDateTime updated;
}
