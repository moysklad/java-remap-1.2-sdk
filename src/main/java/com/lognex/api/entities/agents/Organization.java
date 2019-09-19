package com.lognex.api.entities.agents;

import com.lognex.api.entities.AgentAccount;
import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.CompanyType;
import com.lognex.api.entities.Group;
import com.lognex.api.entities.IEntityWithAttributes;
import com.lognex.api.entities.discounts.BonusProgram;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Юридическое Лицо
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class Organization extends Agent implements IEntityWithAttributes {
    /**
     * ID синхронизации
     */
    private String syncId;

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
     * Комментарий
     */
    private String description;

    /**
     * Код юрлица
     */
    private String code;

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
    private ListEntity<AgentAccount> accounts;

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

    /**
     * Юридический адрес юрлица
     */
    private String legalAddress;

    /**
     * ИНН
     */
    private String inn;

    /**
     * КПП
     */
    private String kpp;

    /**
     * ОГРН
     */
    private String ogrn;

    /**
     * ОГРНИП
     */
    private String ogrnip;

    /**
     * ОКПО
     */
    private String okpo;

    /**
     * Номер свидетельства
     */
    private String certificateNumber;

    /**
     * Дата свидетельства
     */
    private LocalDateTime certificateDate;

    /**
     * Коллекция доп. полей
     */
    private List<Attribute> attributes;

    private String phone;
    private String fax;
    private String fsrarId;
    private String utmUrl;
    private String actualAddress;

    /**
     * Номер договора с ЦРПТ
     */
    private String trackingContractNumber;

    /**
     * Дата договора с ЦРПТ
     */
    private LocalDateTime trackingContractDate;

    /**
     * Бонусная программа
     */
    private BonusProgram bonusProogram;

    /**
     * Бонусные баллы
     */
    private Long bonusPoints;

    public Organization(String id) {
        super(id);
    }
}
