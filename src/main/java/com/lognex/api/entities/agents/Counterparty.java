package com.lognex.api.entities.agents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.discounts.Discount;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Контрагент
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class Counterparty extends Agent implements IEntityWithAttributes {
    /**
     * Владелец
     */
    private Employee owner;

    /**
     * Общий доступ
     */
    private Boolean shared;

    /**
     * Код
     */
    private String code;

    /**
     * Отдел сотрудника
     */
    private Group group;

    /**
     * ID синхронизации
     */
    private String syncId;

    /**
     * Момент последнего обновления
     */
    private LocalDateTime updated;

    /**
     * Комментарий
     */
    private String description;

    /**
     * Внешний код
     */
    private String externalCode;

    /**
     * Флаг архивного Контрагента
     */
    private Boolean archived;

    /**
     * Дата создания
     */
    private LocalDateTime created;

    /**
     * Тип
     */
    private CompanyType companyType;

    /**
     * Полное наименование
     */
    private String legalTitle;

    /**
     * Юридический адрес
     */
    private String legalAddress;

    /**
     * Структурированный Юридический адрес
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
     * Адрес электронной почты
     */
    private String email;

    /**
     * Номер городского телефона
     */
    private String phone;

    /**
     * Номер факса
     */
    private String fax;

    /**
     * Фактический адрес
     */
    private String actualAddress;

    /**
     * Структурированный Фактический адрес
     * */
    private Address actualAddressFull;

    /**
     * Счета
     */
    private ListEntity<AgentAccount> accounts;

    /**
     * Группы
     */
    private List<String> tags;

    /**
     * Скидки
     */
    private List<DiscountData> discounts;

    /**
     * Контактные лица фирмы Контрагента
     */
    private ListEntity<ContactPerson> contactpersons;

    /**
     * События
     */
    private ListEntity<Note> notes;

    /**
     * Номер дисконтной карты
     */
    private String discountCardNumber;

    /**
     * Статус Контрагента
     */
    private State state;

    /**
     * Сумма продаж
     */
    private Integer salesAmount;

    /**
     * Дополнительные поля
     */
    private List<Attribute> attributes;

    /**
     * Цена
     */
    private PriceType priceType;

    public Counterparty(String id) {
        super(id);
    }

    /**
     * Скидка
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class DiscountData {
        /**
         * Скидка
         */
        private Discount discount;

        /**
         * Значение скидки
         */
        private Double personalDiscount;

        /**
         * Коррекция суммы накоплений по скидке
         */
        private Double demandSumCorrection;
    }
}
