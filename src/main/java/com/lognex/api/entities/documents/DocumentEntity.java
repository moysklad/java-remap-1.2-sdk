package com.lognex.api.entities.documents;

import com.lognex.api.entities.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность, имеющая поле Метаданных
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class DocumentEntity extends MetaEntity {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Gtd {
        private String name;
    }

    /**
     * Накладные расходы
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Overhead {
        private Long sum;
        private DistributionType distribution;

        /**
         * Тип распределения накладных расходов
         */
        public enum DistributionType {
            /**
             * Распределение накладных расходов по весу
             */
            weight,

            /**
             * Распределение накладных расходов по объёму
             */
            volume,

            /**
             * Распределение накладных расходов по цене
             */
            price
        }
    }
}
