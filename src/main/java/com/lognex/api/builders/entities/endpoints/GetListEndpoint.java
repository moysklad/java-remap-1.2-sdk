package com.lognex.api.builders.entities.endpoints;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.HttpRequestBuilder;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface GetListEndpoint<T extends MetaEntity> extends Endpoint {
    ListResponse<T> get() throws IOException, LognexApiException;

    default ListResponse<T> get(LognexApi api, Class<T> cl) throws IOException, LognexApiException {
        return HttpRequestBuilder.
                path(api, path()).
                list(cl);
    }
}
