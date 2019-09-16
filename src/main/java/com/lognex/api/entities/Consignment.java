package com.lognex.api.entities;

import com.lognex.api.entities.products.markers.ConsignmentParentMarker;
import com.lognex.api.entities.products.markers.ProductMarker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Серия
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Consignment extends MetaEntity implements ProductMarker {
    /**
     * Описание Серии
     */
    private String description;

    /**
     * Код Серии
     */
    private String code;

    /**
     * Внешний код
     */
    private String externalCode;

    /**
     * Метаданные, представляющие собой ссылку на товар или модификацию
     */
    private ConsignmentParentMarker assortment;

    /**
     * Метка серии
     */
    private String label;

    /**
     * Момент последнего обновления сущности
     */
    private LocalDateTime updated;

    /**
     * Изображение товара
     */
    private Image image;

    /**
     * Штрихкоды серии
     */
    private List<Barcode> barcodes;

    public Consignment(String id) {
        super(id);
    }
}
