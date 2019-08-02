package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.MetaHrefUtils;

import java.io.IOException;

import static com.lognex.api.utils.Constants.API_PATH;

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
