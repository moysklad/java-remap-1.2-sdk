package ru.moysklad.remap_1_2.responses.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.products.Variant;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VariantMetadataResponse extends MetadataAttributeSharedPriceTypesResponse {
    private List<Variant.Characteristic> characteristics;
}
