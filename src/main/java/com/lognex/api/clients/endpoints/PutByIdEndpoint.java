package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface PutByIdEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default void put(String id, T updatedEntity) throws IOException, LognexApiException {
        T responseEntity = HttpRequestExecutor
                .path(api(), path() + id)
                .body(updatedEntity)
                .put((Class<T>) entityClass());

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    default void put(T updatedEntity) throws IOException, LognexApiException {
        put(updatedEntity.getId(), updatedEntity);
    }
}
