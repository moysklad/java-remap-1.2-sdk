package com.lognex.api.entities.discounts;

import com.lognex.api.entities.ProductFolderEntity;
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
public class AccumulationDiscountEntity extends DiscountEntity {
    private List<ProductFolderEntity> productFolders;
    public List<AccumulationLevel> levels;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class AccumulationLevel {
        private Long amount;
        private Integer discount;
    }
}
