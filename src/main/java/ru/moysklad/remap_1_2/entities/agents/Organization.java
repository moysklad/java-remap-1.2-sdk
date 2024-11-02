package ru.moysklad.remap_1_2.entities.agents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.discounts.BonusProgram;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Юридическое Лицо
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class Organization extends Agent implements IEntityWithAttributes<Attribute> {
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
     * Структурированный Юридический адрес юрлица
     * */
    private Address legalAddressFull;

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
    private Address actualAddressFull;

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

    /**
     * Налоговая ставка для авансов для плательщиков НДС
     */
    private BigDecimal advancePaymentVat;

    public Organization(String id) {
        super(id);
    }
}
