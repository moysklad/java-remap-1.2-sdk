package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.agents.Employee;

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
