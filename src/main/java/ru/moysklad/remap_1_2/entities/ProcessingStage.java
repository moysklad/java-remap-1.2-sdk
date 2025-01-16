package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.agents.Employee;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessingStage extends MetaEntity {
    private String description;
    private Boolean archived;
    private String externalCode;
    private Group group;
    private Employee owner;
    private Boolean shared;
    private LocalDateTime updated;
    private Double standardHourCost;
}