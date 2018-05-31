package com.lognex.api.builders.entities.endpoints;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestBuilder;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface PutEndpoint<T extends MetaEntity> extends Endpoint {
    void put(T updatedEntity) throws IOException, LognexApiException;

    default void put(LognexApi api, T updatedEntity, Class<T> cl) throws IOException, LognexApiException {
        T responseEntity = HttpRequestBuilder.
                path(api, path()).
                body(updatedEntity).
                put(cl);

        updatedEntity.set(responseEntity);
    }
}
