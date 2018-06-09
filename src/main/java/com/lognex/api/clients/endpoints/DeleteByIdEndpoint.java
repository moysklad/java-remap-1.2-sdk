package com.lognex.api.clients.endpoints;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface DeleteByIdEndpoint<T extends MetaEntity> extends Endpoint {
    void delete(String id) throws IOException, LognexApiException;

    default void delete(LognexApi api, String id) throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api, path() + id).
                delete();
    }
}
