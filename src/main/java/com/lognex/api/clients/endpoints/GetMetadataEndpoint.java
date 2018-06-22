package com.lognex.api.clients.endpoints;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface GetMetadataEndpoint<T extends MetaEntity> extends Endpoint {
    T metadata() throws IOException, LognexApiException;

    default T metadata(LognexApi api, Class<T> cl) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api, path() + "/metadata").
                get(cl);
    }
}
