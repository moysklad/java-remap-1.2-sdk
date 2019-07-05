package com.lognex.api.entities.documents;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.PriceTypeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.products.markers.SingleProductMarker;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PricelistDocumentEntity extends DocumentEntity {
    /**
     * Внешний код
     */
    private String externalCode;

    /**
     * Печатные формы
     */
    private ListEntity<DocumentEntity> documents;

    /**
     * Тип цены
     */
    private PriceTypeEntity priceType;

    /**
     * Позиции в Прайс-листе
     */
    private ListEntity<PricelistRow> positions;

    /**
     * Юридическое лицо
     */
    private OrganizationEntity organization;

    /**
     * Столбцы описания таблицы
     */
    private List<ColumnsItem> columns;

    public PricelistDocumentEntity(String id) {
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
