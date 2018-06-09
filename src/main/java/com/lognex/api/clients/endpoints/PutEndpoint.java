package com.lognex.api.clients.endpoints;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface PutEndpoint<T extends MetaEntity> extends Endpoint {
    void put(T updatedEntity) throws IOException, LognexApiException;

    default void put(LognexApi api, T updatedEntity, Class<T> cl) throws IOException, LognexApiException {
        T responseEntity = HttpRequestExecutor.
                path(api, path()).
                body(updatedEntity).
                put(cl);

        updatedEntity.set(responseEntity);
    }
}
