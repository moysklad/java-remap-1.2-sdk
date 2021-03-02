package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.IOException;

public interface GetByIdEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T get(String id, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + id).
                apiParams(params).
                get((Class<T>) entityClass());
    }

    @ApiEndpoint
    default T get(MetaEntity entity, ApiParam... params) throws IOException, ApiClientException {
        return get(entity.getId(), params);
    }
}
