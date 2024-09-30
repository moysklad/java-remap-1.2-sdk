package ru.moysklad.remap_1_2.responses.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Attribute;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetadataAttributeSharedPriceTypesResponse extends MetadataAttributeSharedResponse<Attribute> {
    private List<PriceTypeMeta> priceTypes;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    private class PriceTypeMeta {
        private String name;
    }
}
