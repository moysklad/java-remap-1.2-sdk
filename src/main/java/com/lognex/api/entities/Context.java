package com.lognex.api.entities;

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
