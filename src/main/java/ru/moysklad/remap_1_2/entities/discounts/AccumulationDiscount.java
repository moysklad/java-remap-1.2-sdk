package ru.moysklad.remap_1_2.entities.discounts;

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
public class AccumulationDiscount extends GoodDiscount {
    public List<AccumulationLevel> levels;

    public AccumulationDiscount(String id) {
        super(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class AccumulationLevel {
        private Double amount;
        private Double discount;
    }
}
