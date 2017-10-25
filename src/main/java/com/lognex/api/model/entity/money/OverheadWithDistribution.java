package com.lognex.api.model.entity.money;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OverheadWithDistribution {
    private double sum;
    private String distribution; //todo Распределение накладных расходов [weight, volume, price] -> [по весу, по объёму, по цене]
}
