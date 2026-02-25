package ru.moysklad.remap_1_2.entities.agents;

import lombok.NoArgsConstructor;
import ru.moysklad.remap_1_2.entities.Fetchable;
import ru.moysklad.remap_1_2.entities.MetaEntity;

/**
 * Сущность агентов
 */
@NoArgsConstructor
public abstract class Agent extends MetaEntity implements Fetchable {
    public Agent(String id) {
        super(id);
    }
}
