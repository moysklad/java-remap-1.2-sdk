package ru.moysklad.remap_1_2.entities.products;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Assortment;
import ru.moysklad.remap_1_2.entities.Barcode;
import ru.moysklad.remap_1_2.entities.Image;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.Price;
import ru.moysklad.remap_1_2.entities.products.markers.*;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Модификация
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Variant extends Assortment implements SingleProductMarker, ConsignmentParentMarker, ProductMarker, HasImages, ProductAttributeMarker {
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
     * Изображения модификации
     * */

    private ListEntity<Image> images;

    /**
     * Характеристика Модификации
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Characteristic extends MetaEntity {

        public Characteristic(String name) {
            this.setName(name);
        }

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