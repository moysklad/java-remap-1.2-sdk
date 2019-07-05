package com.lognex.api.entities.discounts;

import com.lognex.api.entities.MetaEntity;
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
public class DiscountEntity extends MetaEntity {
    /**
     * Индикатор, является ли скидка активной на данный момент
     */
    private Boolean active;

    /**
     * Индикатор, действует ли скидка на все товары
     */
    private Boolean allProducts;

    /**
     * Теги контрагентов, к которым применяется скидка (если применяется не ко всем контрагентам)
     */
    private List<String> agentTags;

    /**
     * Товары и услуги, которые были выбраны для применения скидки (если применяется не ко всем товарам)
     */
    private List<ProductMarker> assortment;

    public DiscountEntity(String id) {
        super(id);
    }
}
