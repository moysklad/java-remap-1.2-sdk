package com.lognex.api.responses.metadata;

import com.lognex.api.entities.products.VariantEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VariantMetadataResponse extends MetadataAttributeSharedPriceTypesResponse {
    private List<VariantEntity.Characteristic> characteristics;
}
