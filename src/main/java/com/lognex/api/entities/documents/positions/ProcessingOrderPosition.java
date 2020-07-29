package com.lognex.api.entities.documents.positions;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.Product;
import com.lognex.api.entities.products.markers.ProductMarker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessingOrderPosition extends MetaEntity {
    private ProductMarker assortment;
    private Product.ProductPack pack;
    private Double quantity;
    private Double reserve;
}
