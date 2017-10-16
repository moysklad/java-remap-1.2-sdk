package com.lognex.api.model.entity.good;

import com.lognex.api.model.entity.Uom;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductPack {

    private Uom uom;
    private double quantity;
}
