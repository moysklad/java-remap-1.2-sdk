package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.IOException;

public interface GetEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T get(ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path()).
                apiParams(params).
                get((Class<T>) entityClass());
    }
}
