package com.lognex.api.entities.discounts;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoundOffDiscount extends Discount {

    public RoundOffDiscount(String id) {
        super(id);
    }
}
