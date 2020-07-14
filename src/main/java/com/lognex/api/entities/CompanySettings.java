package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanySettings extends MetaEntity {
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

    /**
     * Использовать сквозную нумерацию локументов
     * */

    private Boolean globalOperationNumbering;

    /**
     * Запретить отгрузку отсутствующих товаров
     * */

    private Boolean checkShippingStock;

    /**
     * Автоматически устанавливать минимальную цену
     * */

    private Boolean checkMinPrice;

    /**
     * Использовать корзину
     * */

    private Boolean useRecycleBin;

    /**
     * Использовать адрес компании для электронных писем
     * */

    private Boolean useCompanyAddress;

    /**
     * Адрес компании для электронных писем
     * */

    private String companyAddress;
}
