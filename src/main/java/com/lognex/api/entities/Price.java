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
    public String priceType;
    public MetaEntity currency;
    public Double value;
}
