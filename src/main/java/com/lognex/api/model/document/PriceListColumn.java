package com.lognex.api.model.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PriceListColumn {
    private String name;
    private double percentageDiscount;
}
