package ru.moysklad.remap_1_2.entities;

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
public class Price {
    /**
     * Название цены
     */
    private PriceType priceType;

    /**
     * Валюта цены
     */
    private Currency currency;

    /**
     * Значение (в копейках)
     */
    private Double value;
}
