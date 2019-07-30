package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;
import java.util.List;

public interface GetPlainListEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default List<T> get(ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path()).
                apiParams(params).
                plainList((Class<T>) entityClass());
    }
}
