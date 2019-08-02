package com.lognex.api.clients.endpoints;

import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;

import java.io.IOException;

public interface DeleteEndpoint extends Endpoint {
    @ApiEndpoint
    default void delete() throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path()).
                delete();
    }
}
