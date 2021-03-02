package ru.moysklad.remap_1_2.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UniqueCodeRules {
    /**
     * Проверка уникальности кода сущностей справочника товаров
     */
    private Boolean checkUniqueCode;

    /**
     * Устанавливать уникальный код при создании создании сущностей справочника товаров
     */
    private Boolean fillUniqueCode;
}
