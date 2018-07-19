package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface GetEndpoint<T extends MetaEntity> extends Endpoint {
    default T get(String... expand) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path()).
                expand(expand).
                get((Class<T>) entityClass());
    }
}
