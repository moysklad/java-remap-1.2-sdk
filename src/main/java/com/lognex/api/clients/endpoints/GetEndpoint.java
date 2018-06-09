package com.lognex.api.clients.endpoints;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface GetEndpoint<T extends MetaEntity> extends Endpoint {
    T get() throws IOException, LognexApiException;

    default T get(LognexApi api, Class<T> cl) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api, path()).
                get(cl);
    }
}
