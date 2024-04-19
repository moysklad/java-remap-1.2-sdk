package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProcessingProcess extends MetaEntity {
    private String description;
    private Boolean archived;
    private ListEntity<ProcessingProcessPosition> positions;
    private String externalCode;
    private Group group;
    private Employee owner;
    private Boolean shared;
    private LocalDateTime updated;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ProcessingProcessPosition extends MetaEntity {
        private ProcessingStage processingstage;
    }
}