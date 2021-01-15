package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.MetaHrefUtils;

import java.io.IOException;

import static ru.moysklad.remap_1_2.utils.Constants.API_PATH;

public interface PutByIdEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default void update(String id, T updatedEntity) throws IOException, ApiClientException {
        MetaHrefUtils.fillMeta(updatedEntity, api().getHost() + API_PATH);
        T responseEntity = HttpRequestExecutor
                .path(api(), path() + id)
                .body(updatedEntity)
                .put((Class<T>) entityClass());

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    default void update(T updatedEntity) throws IOException, ApiClientException {
        update(updatedEntity.getId(), updatedEntity);
    }
}
