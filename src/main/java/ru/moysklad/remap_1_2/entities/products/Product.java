package ru.moysklad.remap_1_2.entities.products;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.entities.products.markers.ConsignmentParentMarker;
import ru.moysklad.remap_1_2.entities.products.markers.HasImages;
import ru.moysklad.remap_1_2.entities.products.markers.SingleProductMarker;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Товар
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractProduct implements SingleProductMarker, ConsignmentParentMarker, IEntityWithAttributes<Attribute>, HasImages {
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
    private Integer variantsCount;

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
     * Код вида номенклатурной классификации медицинских средств индивидуальной защиты
     */
    private String ppeType;

    /**
     * Флаг включения частичного выбытия
     */
    private Boolean partialDisposal;

    /**
     * Флаг разливного товара
     */
    private Boolean onTap;

    /**
     * Флаг для отображения запрета скидки
     */
    private Boolean discountProhibited;

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
         * Объем
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
    public static class ProductPack {
        private String id;
        private Uom uom;
        private Double quantity;
        private List<Barcode> barcodes;
    }

    /**
     * Тип маркируемой продукции
     */
    private TrackingType trackingType;

    public enum TrackingType {
        NOT_TRACKED,
        TOBACCO,
        SHOES,
        LP_CLOTHES,
        LP_LINENS,
        PERFUMERY,
        ELECTRONICS,
        TIRES,
        OTP,
        MILK,
        WATER,
        NCP,
        BEER_ALCOHOL,
        FOOD_SUPPLEMENT,
        SANITIZER,
        MEDICAL_DEVICES,
        SOFT_DRINKS,
        VETPHARMA,
        SEAFOOD,
        BICYCLE,
        NABEER
    }

    /**
     * Код ТН ВЭД
     */
    private String tnved;

    /**
     * Признак предмета расчета
     */
    private GoodPaymentItemType paymentItemType;

    /**
     * Код системы налогообложения
     */
    private GoodTaxSystem taxSystem;
}