package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Системная валюта
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SystemCurrency extends AbstractCurrency {

    /**
     * Способ обновления курса для системной Валюты
     */
    private AbstractCurrency.UpdateType rateUpdateType;

    /**
     * Основана ли эта Валюта на Валюте из системного справочника
     */
    final private Boolean system = true;
}

