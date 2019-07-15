package com.lognex.api.entities;

import com.lognex.api.entities.agents.Employee;
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
public final class Context {
    private Employee employee;
}
