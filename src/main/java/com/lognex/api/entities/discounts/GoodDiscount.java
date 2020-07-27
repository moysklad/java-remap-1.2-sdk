package com.lognex.api.entities.discounts;

import com.lognex.api.entities.ProductFolder;
import com.lognex.api.entities.products.markers.ProductMarker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class GoodDiscount extends Discount {

    public GoodDiscount(String id) {
        super(id);
    }

    /**
     * Индикатор, действует ли скидка на все товары
     * */
    private Boolean allProducts;

    /**
     * Товары и услуги, которые были выбраны для применения скидки (если применяется не ко всем товарам)
     */
    private List<ProductMarker> assortment;

    /**
     * Группы товаров которые были выбраны для применения скидки (если применяется не ко всем товарам)
     * */
    private List<ProductFolder> productFolders;
}
