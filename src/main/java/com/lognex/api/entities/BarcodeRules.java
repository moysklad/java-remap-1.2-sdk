package com.lognex.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BarcodeRules {
    /**
     * Автоматически создавать штрихкод EAN13 для новых товаров, комплектов, модификаций и услуг
     */
    private Boolean fillEAN13Barcode;

    /**
     * Использовать префиксы штрихкодов для весовых товаров
     */
    private Boolean weightBarcode;

    /**
     * 	Префикс штрихкодов для весовых товаров. Возможные значения: число формата X или XX
     */
    private Integer weightBarcodePrefix;
}
