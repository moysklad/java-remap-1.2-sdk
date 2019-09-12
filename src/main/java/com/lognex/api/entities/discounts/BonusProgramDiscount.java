package com.lognex.api.entities.discounts;

import com.lognex.api.entities.Fetchable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Бонусная программа
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BonusProgramDiscount extends Discount implements Fetchable {
    private Long earnRateRoublesToPoint;
    private Long spendRatePointsToRouble;
    private Long maxPaidRatePercents;
}
