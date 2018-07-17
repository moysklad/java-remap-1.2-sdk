package com.lognex.api.entities.products;

import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.products.markers.ConsignmentParentMarker;
import com.lognex.api.entities.products.markers.SingleProductMarker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Товар
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductEntity extends AbstractProductEntity implements SingleProductMarker, ConsignmentParentMarker {
    /**
     * Идентификатор сущности
     */
    private String id;

    /**
     * Идентификатор
     */
    private String accountId;

    /**
     * Сотрудник-владелец
     */
    private EmployeeEntity owner;

    /**
     * Флаг общего доступа
     */
    private Boolean shared;

    /**
     * Отдел сотрудника-владельца
     */
    private GroupEntity group;

    /**
     * Идентификатор синхронизации
     */
    private String syncId;

    /**
     * Версия сущности
     */
    private Integer version;

    /**
     * Последнее обновление
     */
    private LocalDateTime updated;

    /**
     * Внешний код
     */
    private String externalCode;

    /**
     * Флаг архивного товара
     */
    private Boolean archived;

    /**
     * Наименование группы, в которую входит товар
     */
    private String pathName;

    /**
     * Изображение товара
     */
    private String image;

    /**
     * Поставщик
     */
    private AgentEntity supplier;

    // attributes
    // country
    /**
     * Артикул
     */
    private String article;

    // weighted

    /**
     * Флаг табачной продукции
     */
    private Boolean tobacco;

    /**
     * Вес товара
     */
    private Double weight;

    /**
     * Объём товара
     */
    private Double volume;

    // packs

    /**
     * Данные алкогольной продукции
     */
    private AlcoholEntity alcoholic;

    /**
     * Количество модификаций товара
     */
    private Integer modificationsCount;

    /**
     * Флаг учёта по серийным номерам
     */
    private Boolean isSerialTrackable;

    /**
     * Флаг весового товара
     */
    private Boolean weighed;

    /**
     * Серийные номера
     */
    private List<String> things;

    /**
     * Неснижаемый остаток
     */
    private Double minimumBalance;

    /**
     * Объект, содержащий данные алкогольной продукции
     */
    public static class AlcoholEntity {
        /**
         * Содержит акцизную марку
         */
        private boolean excise;

        /**
         * Код вида продукции
         */
        private Integer type;

        /**
         * Крепость
         */
        private Integer strength;

        /**
         * Объём тары
         */
        private Integer volume;
    }
}