package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedPriceTypesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public interface ProductMetadataEndpoint extends MetadataEndpoint {
    @ApiEndpoint
    default MetadataAttributeSharedPriceTypesResponse metadata() throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), "/entity/product/metadata").
                get(MetadataAttributeSharedPriceTypesResponse.class);
    }
}