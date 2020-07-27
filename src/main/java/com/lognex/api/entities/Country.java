package com.lognex.api.entities;

import com.lognex.api.entities.agents.Employee;
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

    /**
     * Ссылка на Владельца (Сотрудника) в формате Метаданных
     */
    private Employee owner;

    /**
     * Общий доступ
     */
    private Boolean shared;

    /**
     * Отдел сотрудника в формате Метаданных
     */
    private Group group;

    public Country(String id) {
        super(id);
    }
}
