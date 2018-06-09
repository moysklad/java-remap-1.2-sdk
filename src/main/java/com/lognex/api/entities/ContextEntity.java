package com.lognex.api.entities;

import com.lognex.api.entities.agents.EmployeeEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Контекст запроса
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public final class ContextEntity {
    private EmployeeEntity employee;
}
