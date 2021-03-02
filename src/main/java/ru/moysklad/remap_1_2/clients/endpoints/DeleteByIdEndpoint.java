package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.ApiClientException;

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
