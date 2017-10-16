package com.lognex.api.model.entity.good;

import com.lognex.api.model.base.AbstractEntityLegendable;
import com.lognex.api.model.entity.Price;
import com.lognex.api.model.entity.Uom;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractGood extends AbstractEntityLegendable {

    private boolean archived;
    private String pathName;
    private double vat;
    private double effectiveVat;
    private ProductFolder productFolder;
    private Uom uom;
    private Price minPrice; //todo double? Минимальная цена
    private List<Price> salePrices;
    private List<Attribute> attributes;
}
