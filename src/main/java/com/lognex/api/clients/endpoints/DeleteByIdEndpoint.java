package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.ApiClientException;

import java.io.IOException;

public interface DeleteByIdEndpoint extends Endpoint {
    @ApiEndpoint
    default void delete(String id) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + id).
                delete();
    }

    @ApiEndpoint
    default void delete(MetaEntity entity) throws IOException, ApiClientException {
        delete(entity.getId());
    }
}
