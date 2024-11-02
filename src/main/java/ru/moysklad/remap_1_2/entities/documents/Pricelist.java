package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.entities.products.markers.SingleProductMarker;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Pricelist extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    /**
     * Внешний код
     */
    private String externalCode;

    /**
     * Тип цены
     */
    private PriceType priceType;

    /**
     * Позиции в Прайс-листе
     */
    private ListEntity<PricelistRow> positions;

    /**
     * Юридическое лицо
     */
    private Organization organization;

    /**
     * Столбцы описания таблицы
     */
    private List<ColumnsItem> columns;

    /**
     * ID синхронизации
     */
    private String syncId;

    /**
     * Дата создания
     */
    private LocalDateTime created;

    /**
     * Дата удаления
     */
    private LocalDateTime deleted;

    /**
     * Комментарий
     */
    private String description;

    /**
     * Статус договора в формате Метаданных
     */
    private State state;

    /**
     * Коллекция доп. полей
     */
    private List<DocumentAttribute> attributes;

    private ListEntity<AttachedFile> files;

    public Pricelist(String id) {
        super(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class PricelistRow extends MetaEntity {
        /**
         * Значения столбцов
         */
        private List<CellsItem> cells;

        /**
         * Товар/услуга/модификация, которую представляет собой позиция
         */
        private SingleProductMarker assortment;

        /**
         * Упаковка товара
         */
        private Product.ProductPack pack;

        @Getter
        @Setter
        @NoArgsConstructor
        @EqualsAndHashCode
        public static class CellsItem {
            /**
             * Название столбца, к которому относится данная ячейка
             */
            private String column;

            /**
             * Числовое значение ячейки
             */
            private Long sum;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class ColumnsItem {
        /**
         * Название столбца
         */
        private String name;

        /**
         * Процентная наценка или скидка по умолчанию для столбца
         */
        private Integer percentageDiscount;
    }
}
