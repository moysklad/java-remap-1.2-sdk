package com.lognex.api.entities.products;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Employee;
import com.lognex.api.entities.products.markers.ConsignmentParentMarker;
import com.lognex.api.entities.products.markers.HasImages;
import com.lognex.api.entities.products.markers.SingleProductMarker;
import com.lognex.api.responses.ListEntity;
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
public class Product extends AbstractProduct implements SingleProductMarker, ConsignmentParentMarker, IEntityWithAttributes, HasImages {
    /**
     * Сотрудник-владелец
     */
    private Employee owner;

    /**
     * Флаг общего доступа
     */
    private Boolean shared;

    /**
     * Отдел сотрудника-владельца
     */
    private Group group;

    /**
     * Идентификатор синхронизации
     */
    private String syncId;

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
     * Изображения товаров
     */
    private ListEntity<Image> images;

    /**
     * Поставщик
     */
    private Agent supplier;

    /**
     * Дополнительные поля
     */
    private List<Attribute> attributes;

    /**
     * Страна
     */
    private Country country;

    /**
     * Артикул
     */
    private String article;

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

    /**
     * Упаковка
     */
    private List<ProductPack> packs;

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
    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
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
        private Double strength;

        /**
         * Объём тары
         */
        private Double volume;
    }

    /**
     * Объект, содержащий данные упаковки
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    private class ProductPack {
        private String id;
        private Uom uom;
        private Double quantity;
    }

    /**
     * Тип маркируемой продукции
     */
    private TrackingType trackingType;

    public enum TrackingType {
        NOT_TRACKED,
        TOBACCO,
        SHOES
    }
}