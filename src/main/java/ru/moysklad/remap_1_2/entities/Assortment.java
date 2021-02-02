package ru.moysklad.remap_1_2.entities;

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
