package ru.moysklad.remap_1_2.entities.documents;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Group;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.agents.Employee;

import java.time.LocalDateTime;

/**
 * Сущность, имеющая поле Метаданных
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class DocumentEntity extends MetaEntity {
    private Group group;
    private LocalDateTime updated;
    private Boolean shared;
    private Employee owner;
    private LocalDateTime moment;
    private Boolean applicable;
    private Long sum;
    private Boolean printed;
    private Boolean published;

    public DocumentEntity(String id) {
        super(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
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
