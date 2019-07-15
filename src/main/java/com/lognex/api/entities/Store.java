package com.lognex.api.entities;

import com.lognex.api.entities.agents.Employee;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Склад
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Store extends MetaEntity implements IEntityWithAttributes {
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

    /**
     * Момент последнего обновления сущности
     */
    private LocalDateTime updated;

    /**
     * комментарий к Складу
     */
    private String description;

    /**
     * Код Склада
     */
    private String code;

    /**
     * Внешний код Склада
     */
    private String externalCode;

    /**
     * Добавлен ли Склад в архив
     */
    private Boolean archived;

    /**
     * Адрес Склада
     */
    private String address;

    /**
     * Родительский склад (Группа)
     */
    private Store parent;

    /**
     * Группа Склада
     */
    private String pathName;

    /**
     * Дополнительные поля Склада в формате Метаданных
     */
    private List<Attribute> attributes;

    public Store(String id) {
        super(id);
    }
}
