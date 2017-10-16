package com.lognex.api.model.entity.good;

import com.lognex.api.model.entity.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Overhead {//todo maybe make priceWithCurrency class
    private double value;
    private Currency currency;
}
