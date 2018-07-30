package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Цена
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PriceEntity {
    /**
     * Название цены
     */
    private String priceType;

    /**
     * Валюта цены
     */
    private CurrencyEntity currency;

    /**
     * Значение (в копейках)
     */
    private Long value;
}
