package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface PostEndpoint<T extends MetaEntity> extends Endpoint {
    default T post(T newEntity) throws IOException, LognexApiException {
        T responseEntity = HttpRequestExecutor.
                path(api(), path()).
                body(newEntity).
                post((Class<T>) entityClass());

        newEntity.set(responseEntity);
        return newEntity;
    }
}
