package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.entity.good.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProcessingPlanProduct extends AbstractEntity {

    private Product product;
    private double quantity;
}
