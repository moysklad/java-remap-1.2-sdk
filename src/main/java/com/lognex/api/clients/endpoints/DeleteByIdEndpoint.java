package com.lognex.api.clients.endpoints;

import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface DeleteByIdEndpoint extends Endpoint {
    default void delete(String id) throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api(), path() + id).
                delete();
    }
}
