package com.lognex.api.clients.endpoints;

import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface DeleteEndpoint extends Endpoint {
    default void delete() throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api(), path()).
                delete();
    }
}
