package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityWithOwner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Price extends AbstractEntityWithOwner {

    private double value;
    private Currency currency;
    private PriceType priceType;
}
