package com.lognex.api.entities.agents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.discounts.DiscountEntity;
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
public final class CounterpartyEntity extends AgentEntity {
    /**
     * Владелец
     */
    private EmployeeEntity owner;

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
    private GroupEntity group;

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
     * Счета
     */
    private ListEntity<AccountEntity> accounts;

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
    private ListEntity<ContactPersonEntity> contactpersons;

    /**
     * События
     */
    private ListEntity<NoteEntity> notes;

    /**
     * Номер дисконтной карты
     */
    private String discountCardNumber;

    /**
     * Статус Контрагента
     */
    private StateEntity state;

    /**
     * Сумма продаж
     */
    private Integer salesAmount;

    /**
     * Дополнительные поля
     */
    private List<AttributeEntity> attributes;

    /**
     * Цена
     */
    private String priceType;

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
        private DiscountEntity discount;

        /**
         * Значение скидки
         */
        private Double personalDiscount;
    }
}
