package com.lognex.api.entities.agents;

import com.lognex.api.entities.Fetchable;
import com.lognex.api.entities.MetaEntity;
import lombok.NoArgsConstructor;

/**
 * Сущность агентов
 */
@NoArgsConstructor
public abstract class Agent extends MetaEntity implements Fetchable {
    public Agent(String id) {
        super(id);
    }
}
