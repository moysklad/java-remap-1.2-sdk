package com.lognex.api.clients.endpoints;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface GetListEndpoint<T extends MetaEntity> extends Endpoint {
    ListEntity<T> get(String... expand) throws IOException, LognexApiException;

    default ListEntity<T> get(LognexApi api, Class<T> cl, String... expand) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api, path()).
                expand(expand).
                list(cl);
    }
}
