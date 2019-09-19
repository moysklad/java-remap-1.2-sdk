package com.lognex.api.entities;

import lombok.NoArgsConstructor;

/**
 * Абстрактный класс, объединяющий товары, услуги, серии,
 * комплекты и модификации
 */
@NoArgsConstructor
public abstract class Assortment extends MetaEntity {
    protected Assortment(String id) {
        super(id);
    }
}
