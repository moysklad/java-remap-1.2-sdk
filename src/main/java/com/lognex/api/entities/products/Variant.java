package com.lognex.api.entities.products;

import com.lognex.api.entities.Barcode;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Price;
import com.lognex.api.entities.products.markers.ConsignmentParentMarker;
import com.lognex.api.entities.products.markers.ProductMarker;
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
public class Variant extends MetaEntity implements SingleProductMarker, ConsignmentParentMarker, ProductMarker {
    /**
     * Дата последнего обновления сущности
     */
    private LocalDateTime updated;

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
    private Price minPrice;

    /**
     * Закупочная цена
     */
    private Price buyPrice;

    /**
     * Цены продажи
     */
    private List<Price> salePrices;

    /**
     * Штрихкоды
     */
    private List<Barcode> barcodes;

    /**
     * Товар, к которому привязана данная модификация
     */
    private Product product;

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