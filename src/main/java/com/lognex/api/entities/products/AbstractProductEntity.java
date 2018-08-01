package com.lognex.api.entities.products;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.PriceEntity;
import com.lognex.api.entities.ProductFolderEntity;
import com.lognex.api.entities.UomEntity;
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
public abstract class AbstractProductEntity extends MetaEntity implements ProductMarker {
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
    private ProductFolderEntity productFolder;

    /**
     * Минимальная цена
     */
    private Long minPrice;

    /**
     * Закупочная цена
     */
    private PriceEntity buyPrice;

    /**
     * Цены продажи
     */
    private List<PriceEntity> salePrices;

    /**
     * Ссылка на единицы измерения в формате Метаданных
     */
    private UomEntity uom;

    /**
     * Массив штрихкодов товара
     */
    private List<String> barcodes;
}
