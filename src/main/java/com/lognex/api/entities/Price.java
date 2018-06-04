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
public class Price {
    private String priceType;
    private MetaEntity currency;
    private Double value;
}
