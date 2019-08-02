package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;

public interface GetListEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default ListEntity<T> get(ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path()).
                apiParams(params).
                list((Class<T>) entityClass());
    }
}
