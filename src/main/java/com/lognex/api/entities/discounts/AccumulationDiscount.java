package com.lognex.api.entities.discounts;

import com.lognex.api.entities.ProductFolder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Накопительная скидка
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccumulationDiscount extends Discount {
    private List<ProductFolder> productFolders;
    public List<AccumulationLevel> levels;

    public AccumulationDiscount(String id) {
        super(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class AccumulationLevel {
        private Long amount;
        private Integer discount;
    }
}
