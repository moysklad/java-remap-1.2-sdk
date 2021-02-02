package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public interface DeleteEndpoint extends Endpoint {
    @ApiEndpoint
    default void delete() throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path()).
                delete();
    }
}
