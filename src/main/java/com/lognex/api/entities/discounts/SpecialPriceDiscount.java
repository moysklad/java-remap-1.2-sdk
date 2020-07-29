package com.lognex.api.entities.discounts;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.PriceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SpecialPriceDiscount extends GoodDiscount {
    private Boolean usePriceType;
    private SpecialPriceData specialPrice;
    private Double discount;

    public SpecialPriceDiscount(String id) {
        super(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class SpecialPriceData extends MetaEntity {
        private Long value;
        private PriceType priceType;
    }
}
