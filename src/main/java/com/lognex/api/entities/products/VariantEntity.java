package com.lognex.api.entities.products;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.PriceEntity;
import com.lognex.api.entities.products.markers.SingleProductMarker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Модификация
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VariantEntity extends MetaEntity implements SingleProductMarker {
    /**
     * Идентификатор сущности
     */
    private String id;

    /**
     * Идентификатор учётной записи
     */
    private String accountId;

    /**
     * Версия сущности
     */
    private Integer version;

    /**
     * Дата последнего обновления сущности
     */
    private LocalDateTime updated;

    /**
     * Наименование товара с Модификацией
     */
    private String name;

    /**
     * Код модификации
     */
    private String code;

    /**
     * Внешний код модификации
     */
    private String externalCode;

    /**
     * Флаг архивной сущности
     */
    private Boolean archived;

    /**
     * Характеристики Модификации
     */
    private List<Characteristic> characteristics;

    /**
     * Минимальная цена
     */
    private Integer minPrice;

    /**
     * Закупочная цена
     */
    private PriceEntity buyPrice;

    /**
     * Цены продажи
     */
    private List<PriceEntity> salePrices;

    /**
     * Штрихкоды
     */
    private List<String> barcodes;

    /**
     * Товар, к которому привязана данная модификация
     */
    private ProductEntity product;

    /**
     * Серийные номера
     */
    private List<String> things;

    /**
     * Характеристика Модификации
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Characteristic extends MetaEntity {
        /**
         * Идентификатор сущности
         */
        private String id;

        /**
         * Наименование характеристики
         */
        private String name;

        /**
         * Тип характеристики
         */
        private String type;

        /**
         * Флаг обязательной характеристики
         */
        private Boolean required;

        /**
         * Значение характеристики
         */
        private String value;
    }
}