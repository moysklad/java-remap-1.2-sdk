package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface MetadataEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T metadata() throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + "metadata").
                get((Class<T>) metaEntityClass());
    }
}
