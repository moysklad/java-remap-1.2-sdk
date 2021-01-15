package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.MassDeleteResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.MetaHrefUtils;

import java.io.IOException;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.Constants.API_PATH;

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
