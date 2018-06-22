package com.lognex.api.entities.documents;

import com.lognex.api.entities.CountryEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.markers.ProductMarker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DocumentPosition extends MetaEntity {
    private String accountId;
    private CountryEntity country;
    private Double quantity;
    private ProductMarker assortment;
    private Integer price;
    private DocumentEntity.Gtd gtd;
    private Integer overhead;
    private Integer vat;
    private Double discount;
    private String id;
    private List<String> things;
}
