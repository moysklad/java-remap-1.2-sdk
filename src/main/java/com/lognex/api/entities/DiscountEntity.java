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
     * ID скидки в формате UUID
     */
    private String id;

    /**
     * ID учетной записи
     */
    private String accountId;

    /**
     * Наименование скидки
     */
    private String name;

    /**
     * Индикатор, является ли скидка активной на данный момент
     */
    private Boolean active;

    /**
     * Индикатор, действует ли скидка на все товары
     */
    private Boolean allProducts;
}
