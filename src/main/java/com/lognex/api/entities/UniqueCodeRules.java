package com.lognex.api.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
