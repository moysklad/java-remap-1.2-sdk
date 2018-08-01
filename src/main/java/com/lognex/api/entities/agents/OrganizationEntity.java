package com.lognex.api.entities.agents;

import com.lognex.api.entities.CompanyType;
import com.lognex.api.entities.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Юридическое Лицо
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class OrganizationEntity extends AgentEntity {
    /**
     * Ссылка на Владельца (Сотрудника) в формате Метаданных
     */
    private EmployeeEntity owner;

    /**
     * Общий доступ
     */
    private Boolean shared;

    /**
     * Отдел сотрудника в формате Метаданных
     */
    private MetaEntity group;

    /**
     * Момент последнего обновления сущности
     */
    private LocalDateTime updated;

    /**
     * Внешний код юрлица
     */
    private String externalCode;

    /**
     * Добавлено ли юрлицо в архив
     */
    private Boolean archived;

    /**
     * Дата создания
     */
    private LocalDateTime created;

    /**
     * Тип юрлица. [Юридическое лицо, Индивидуальный предприниматель, Физическое лицо]. В зависимости от значения данного поля набор выводимых реквизитов юрлица может меняться. Подробнее тут
     */
    private CompanyType companyType;

    /**
     * Полное наименование юрлица
     */
    private String legalTitle;

    /**
     * Электронная почта
     */
    private String email;

    /**
     * Метаданные, представляющие собой ссылку на счета юрлица
     */
    private MetaEntity accounts;

    /**
     * Включен ли ЕГАИС для данного юрлица
     */
    private Boolean isEgaisEnable;

    /**
     * Является ли данное юрлицо плательщиком НДС
     */
    private Boolean payerVat;

    /**
     * Руководитель
     */
    private String director;

    /**
     * Главный бухгалтер
     */
    private String chiefAccountant;
}
