package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface GetListEndpoint<T extends MetaEntity> extends Endpoint {
    default ListEntity<T> get(String... expand) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path()).
                expand(expand).
                list((Class<T>) entityClass());
    }
}
