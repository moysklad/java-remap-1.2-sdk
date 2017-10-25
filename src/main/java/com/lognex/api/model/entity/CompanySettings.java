package com.lognex.api.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanySettings {
    private enum discountStrategy {bySum,byPriority};

    private Currency currency;
    private discountStrategy discountStrategy;
}
