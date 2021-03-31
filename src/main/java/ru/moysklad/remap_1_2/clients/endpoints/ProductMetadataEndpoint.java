package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public interface ProductMetadataEndpoint<T extends MetaEntity> extends MetadataEndpoint {
    @ApiEndpoint
    default T metadata() throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), "/entity/product/" + "metadata").
                get((Class<T>) metaEntityClass());
    }
}