package com.lognex.api.model.entity.good;

import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.*;
import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.util.ID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseProduct extends ProductFolder implements IEntityWithAttributes, Assortment {
    private double minPrice;
    private Set<Price> salePrices = new HashSet<>();
    private Set<Attribute<?>> attributes = new HashSet<>();

    BaseProduct(ID id) {
        setId(id);
    }


}
