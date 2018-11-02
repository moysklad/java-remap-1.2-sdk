package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;

public interface GetByIdEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T get(String id, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + id).
                apiParams(params).
                get((Class<T>) entityClass());
    }

    @ApiEndpoint
    default T get(MetaEntity entity, ApiParam... params) throws IOException, LognexApiException {
        return get(entity.getId(), params);
    }
}
