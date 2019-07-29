package com.lognex.api.responses;

import com.lognex.api.entities.Currency;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.PriceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanySettingsResponse extends MetaEntity {
    /**
     * Ссылка на стандартную валюту
     */
    private Currency currency;

    /**
     * Список типов цен
     */
    private List<PriceType> priceTypes;

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
