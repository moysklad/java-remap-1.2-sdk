package com.lognex.api.entities.documents;

import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Сущность, имеющая поле Метаданных
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class DocumentEntity extends MetaEntity {
    private GroupEntity group;
    private LocalDateTime updated;
    private Boolean shared;
    private EmployeeEntity owner;
    private LocalDateTime moment;
    private Boolean applicable;
    private Long sum;

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
