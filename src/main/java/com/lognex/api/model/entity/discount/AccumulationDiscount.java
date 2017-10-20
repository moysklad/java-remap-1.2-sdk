package com.lognex.api.model.entity.discount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccumulationDiscount extends Discount {
    private List<DiscountLevel> levels;
}
