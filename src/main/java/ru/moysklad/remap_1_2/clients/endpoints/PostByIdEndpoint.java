package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.MetaHrefUtils;

import java.io.IOException;

import static ru.moysklad.remap_1_2.utils.Constants.API_PATH;

public interface PostByIdEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T create(String id, T newEntity) throws IOException, ApiClientException {
        MetaHrefUtils.fillMeta(newEntity, api().getHost() + API_PATH);
        T responseEntity = HttpRequestExecutor.
                path(api(), path() + id).
                body(newEntity).
                post((Class<T>) entityClass());

        newEntity.set(responseEntity);
        return newEntity;
    }
}
