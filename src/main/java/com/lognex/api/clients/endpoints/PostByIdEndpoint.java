package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface PostByIdEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T post(String id, T newEntity) throws IOException, LognexApiException {
        T responseEntity = HttpRequestExecutor.
                path(api(), path() + id).
                body(newEntity).
                post((Class<T>) entityClass());

        newEntity.set(responseEntity);
        return newEntity;
    }
}
