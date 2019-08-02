package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;

public interface GetBySyncIdEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T getBySyncId(String syncId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + "syncid/" + syncId).
                apiParams(params).
                get((Class<T>) entityClass());
    }
}
