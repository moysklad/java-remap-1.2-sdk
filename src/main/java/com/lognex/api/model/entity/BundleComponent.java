package com.lognex.api.model.entity;

import com.lognex.api.model.base.Entity;
import com.lognex.api.model.entity.good.Assortment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BundleComponent extends Entity {

    private Assortment assortment;
    private double quantity;
}
