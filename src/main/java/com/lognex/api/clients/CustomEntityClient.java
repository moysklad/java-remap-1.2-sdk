package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.CustomEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class CustomEntityClient
        extends ApiClient
        implements
        PostEndpoint<CustomEntity>,
        PutByIdEndpoint<CustomEntity>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<ListEntity<CustomEntity>>,
        PostByIdEndpoint<CustomEntity> {

    public CustomEntityClient(LognexApi api) {
        super(api, "/entity/customentity/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CustomEntity.class;
    }

    @ApiEndpoint
    public CustomEntity getCustomEntityElement(String customEntityMetadataId, String customEntityId) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + customEntityMetadataId + "/" + customEntityId).
                get(CustomEntity.class);
    }

    @ApiEndpoint
    public void deleteCustomEntityElement(String customEntityMetadataId, String customEntityId) throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api(), path() + customEntityMetadataId + "/" + customEntityId).
                delete();
    }

    @ApiEndpoint
    public void deleteCustomEntityElement(String customEntityMetadataId, CustomEntity customEntity) throws IOException, LognexApiException {
        deleteCustomEntityElement(customEntityMetadataId, customEntity.getId());
    }

    @ApiEndpoint
    public void putCustomEntityElement(String customEntityMetadataId, String customEntityId, CustomEntity updatedEntity) throws IOException, LognexApiException {
        CustomEntity responseEntity = HttpRequestExecutor
                .path(api(), path() + customEntityMetadataId + "/" + customEntityId)
                .body(updatedEntity)
                .put(CustomEntity.class);

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    public void putCustomEntityElement(String customEntityMetadataId, CustomEntity customEntity) throws IOException, LognexApiException {
        putCustomEntityElement(customEntityMetadataId, customEntity.getId(), customEntity);
    }
}
