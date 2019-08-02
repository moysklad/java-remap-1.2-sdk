package com.lognex.api.entities.products;

import com.lognex.api.entities.*;
import com.lognex.api.entities.products.markers.ProductMarker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Абстрактный класс, объединяющий основные товары (не модификации)
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractProduct extends MetaEntity implements ProductMarker {
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
