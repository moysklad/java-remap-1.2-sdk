package com.lognex.api.model.entity.discount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscountLevel {
    private double amount;
    private double discount;
}
