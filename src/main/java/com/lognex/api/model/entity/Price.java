package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Price extends AbstractEntity {
    private double value;
    private Currency currency;
    private String priceType;
}
