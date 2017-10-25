package com.lognex.api.model.entity.good;

import com.lognex.api.model.base.EntityLegendable;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.Price;
import com.lognex.api.model.entity.Uom;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public abstract class Good extends EntityLegendable implements IEntityWithAttributes {

    private boolean archived;
    private String pathName;
    private double vat;
    private double effectiveVat;
    private ProductFolder productFolder;
    private Uom uom;
    private double minPrice;
    private List<Price> salePrices;
    private Set<Attribute<?>> attributes = new HashSet<>();
}
