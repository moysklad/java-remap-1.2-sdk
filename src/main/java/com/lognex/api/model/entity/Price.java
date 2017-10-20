package com.lognex.api.model.entity;

import com.lognex.api.model.base.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Price extends Entity {

    private double value;
    private Currency currency;
    private String priceType;
}
