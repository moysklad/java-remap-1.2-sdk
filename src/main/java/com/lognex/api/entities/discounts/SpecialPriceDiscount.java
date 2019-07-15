package com.lognex.api.entities.discounts;

import com.lognex.api.entities.PriceType;
import com.lognex.api.entities.ProductFolder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SpecialPriceDiscount extends Discount {
    private Double discount;
    private SpecialPriceData specialPrice;
    private List<ProductFolder> productFolders;

    public SpecialPriceDiscount(String id) {
        super(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class SpecialPriceData {
        private Long value;
        private PriceType priceType;
    }
}
