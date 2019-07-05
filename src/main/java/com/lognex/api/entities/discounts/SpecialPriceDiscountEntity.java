package com.lognex.api.entities.discounts;

import com.lognex.api.entities.PriceTypeEntity;
import com.lognex.api.entities.ProductFolderEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SpecialPriceDiscountEntity extends DiscountEntity {
    private Double discount;
    private SpecialPriceData specialPrice;
    private List<ProductFolderEntity> productFolders;

    public SpecialPriceDiscountEntity(String id) {
        super(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class SpecialPriceData {
        private Long value;
        private PriceTypeEntity priceType;
    }
}
