package com.lognex.api.clients.endpoints;

import com.lognex.api.responses.MetadataListResponse;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface GetMetadataEndpoint<T extends MetadataListResponse> extends Endpoint {
    default T metadata() throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + "metadata").
                get((Class<T>) metaEntityClass());
    }
}
