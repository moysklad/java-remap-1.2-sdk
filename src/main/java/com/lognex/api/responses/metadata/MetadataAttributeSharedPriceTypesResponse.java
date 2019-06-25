package com.lognex.api.responses.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetadataAttributeSharedPriceTypesResponse extends MetadataAttributeSharedResponse {
    private List<PriceTypeMeta> priceTypes;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    private class PriceTypeMeta {
        private String name;
    }
}
