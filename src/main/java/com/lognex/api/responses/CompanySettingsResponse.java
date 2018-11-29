package com.lognex.api.responses;

import com.lognex.api.entities.CurrencyEntity;
import com.lognex.api.entities.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanySettingsResponse extends MetaEntity {
    /**
     * Ссылка на стандартную валюту
     */
    private CurrencyEntity currency;

    /**
     * Совместное применение скидок
     */
    private DiscountStrategy discountStrategy;

    /**
     * Стратегия совместного применения скидок
     */
    public enum DiscountStrategy {
        /**
         * Сумма скидок (должна действовать сумма скидок)
         */
        bySum,

        /**
         * Приоритетная (должна действовать одна, наиболее выгодная для покупателя скидка)
         */
        byPriority
    }
}
