package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.products.markers.ConsignmentParentMarker;
import ru.moysklad.remap_1_2.entities.products.markers.ProductMarker;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Серия
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Consignment extends Assortment implements ProductMarker {
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
