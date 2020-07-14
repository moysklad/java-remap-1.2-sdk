package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.MassDeleteResponse;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.MetaHrefUtils;

import java.io.IOException;
import java.util.List;

import static com.lognex.api.utils.Constants.API_PATH;

public interface MassCreateUpdateDeleteEndpoint<T extends MetaEntity> extends MassCreateUpdateEndpoint<T> {

    @ApiEndpoint
    default List<MassDeleteResponse> delete(List<T> entities) throws IOException, ApiClientException {
        entities.forEach(newEntity -> MetaHrefUtils.fillMeta(newEntity, api().getHost() + API_PATH));

        return HttpRequestExecutor
                .path(api(), path() + "delete")
                .body(entities)
                .postList(MassDeleteResponse.class);
    }
}
