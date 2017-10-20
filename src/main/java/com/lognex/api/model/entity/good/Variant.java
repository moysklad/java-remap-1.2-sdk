package com.lognex.api.model.entity.good;

import com.lognex.api.model.base.EntityLegendable;
import com.lognex.api.model.entity.Price;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Variant extends EntityLegendable implements Assortment {
    private boolean archived;
    private Map<String, String> characteristics = new HashMap<>();//todo
    private double minPrice;
    private double buyPrice;
    private List<Price> salePrices;
    private Product product;
    private List<Thing> things;
}