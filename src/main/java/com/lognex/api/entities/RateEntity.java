package com.lognex.api.entities;

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
public class RateEntity {
    private CurrencyEntity currency;
    private Double value;
}
