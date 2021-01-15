package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.IOException;

public interface GetBySyncIdEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T getBySyncId(String syncId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + "syncid/" + syncId).
                apiParams(params).
                get((Class<T>) entityClass());
    }
}
