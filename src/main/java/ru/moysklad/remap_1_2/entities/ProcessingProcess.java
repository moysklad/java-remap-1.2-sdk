package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProcessingProcess extends MetaEntity {
    private String description;
    private Boolean archived;
    private ListEntity<ProcessPosition> positions;
    private String externalCode;
    private Group group;
    private Employee owner;
    private Boolean shared;
    private LocalDateTime updated;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ProcessPosition extends MetaEntity {
        private ProcessingStage processingstage;
        Integer orderingposition;
        private List<ProcessNextPosition> nextPositions;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProcessNextPosition {
        private ProcessingStage processingstage;

        public ProcessNextPosition(ProcessingStage processingstage) {
            this.processingstage = processingstage;
        }
    }
}