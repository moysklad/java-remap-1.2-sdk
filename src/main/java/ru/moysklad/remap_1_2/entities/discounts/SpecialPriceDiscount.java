package ru.moysklad.remap_1_2.entities.discounts;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.PriceType;

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
