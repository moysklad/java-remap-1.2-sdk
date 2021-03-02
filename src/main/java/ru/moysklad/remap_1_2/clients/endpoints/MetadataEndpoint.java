package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;

public interface MetadataEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T metadata() throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + "metadata").
                get((Class<T>) metaEntityClass());
    }
}
