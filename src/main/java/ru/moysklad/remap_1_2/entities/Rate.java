package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Валюта документа
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Rate {
    private Currency currency;
    private Double value;
}
