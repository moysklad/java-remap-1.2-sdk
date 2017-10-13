package com.lognex.api.model.base;

import com.lognex.api.model.entity.Assortment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Position extends AbstractEntity {
    private int quantity;
    private long price;
    private double discount;
    private long vat;
    private Assortment assortment;
}
