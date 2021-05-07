package ru.moysklad.remap_1_2.entities.discounts;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Fetchable;

/**
 * Бонусная программа
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BonusProgram extends Discount implements Fetchable {
    private Integer earnRateRoublesToPoint;
    private Integer spendRatePointsToRouble;
    private Integer maxPaidRatePercents;
    private Integer postponedBonusesDelayDays;
}
