package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.join;

public interface GetListEndpoint<T extends MetaEntity> extends Endpoint {
    default ListEntity<T> get(String... expand) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path()).
                expand(expand).
                list((Class<T>) entityClass());
    }

    default ListEntity<T> get(List<String> filterExpressions, String... expand) throws IOException, LognexApiException {
        HttpRequestExecutor executor = HttpRequestExecutor.path(api(), path());
        if(filterExpressions != null && !filterExpressions.isEmpty()) {
            executor.query("filter", join(filterExpressions.toArray(), "&"));
        }
        return executor.expand(expand).list((Class<T>) entityClass());
    }
}
