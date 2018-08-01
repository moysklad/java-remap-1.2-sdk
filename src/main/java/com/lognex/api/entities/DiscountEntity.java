package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DiscountEntity extends MetaEntity {
    /**
     * Индикатор, является ли скидка активной на данный момент
     */
    private Boolean active;

    /**
     * Индикатор, действует ли скидка на все товары
     */
    private Boolean allProducts;
}
