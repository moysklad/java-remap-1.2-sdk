package ru.moysklad.remap_1_2.entities.products;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.products.markers.ProductAttributeMarker;
import ru.moysklad.remap_1_2.entities.products.markers.ProductMarker;

import java.util.List;

/**
 * Абстрактный класс, объединяющий основные товары (не модификации)
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractProduct extends Assortment implements ProductMarker, ProductAttributeMarker {
    /**
     * Код Товара
     */
    private String code;

    /**
     * Описание Товара
     */
    private String description;

    /**
     * НДС %
     */
    private Integer vat;

    /**
     * Реальный НДС %
     */
    private Integer effectiveVat;

    /**
     * Ссылка на группу Товаров в формате Метаданных
     */
    private ProductFolder productFolder;

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
     * Ссылка на единицы измерения в формате Метаданных
     */
    private Uom uom;

    /**
     * Массив штрихкодов товара
     */
    private List<Barcode> barcodes;
}
