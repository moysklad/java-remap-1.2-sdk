package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.Bundle;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeResponse;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;

public final class BundleClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<Bundle>,
        PostEndpoint<Bundle>,
        MetadataEndpoint<MetadataAttributeResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Bundle>,
        PutByIdEndpoint<Bundle>,
        DeleteByIdEndpoint {

    public BundleClientEntity(ApiClient api) {
        super(api, "/entity/bundle/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Bundle.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeResponse.class;
    }

    @ApiEndpoint
    public ListEntity<Bundle.ComponentEntity> getComponents(String bundleId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + bundleId + "/components").
                apiParams(params).
                list(Bundle.ComponentEntity.class);
    }

    @ApiEndpoint
    public ListEntity<Bundle.ComponentEntity> getComponents(Bundle bundle, ApiParam... params) throws IOException, LognexApiException {
        return getComponents(bundle.getId(), params);
    }

    @ApiEndpoint
    public Bundle.ComponentEntity getComponent(String bundleId, String componentId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + bundleId + "/components/" + componentId).
                apiParams(params).
                get(Bundle.ComponentEntity.class);
    }

    @ApiEndpoint
    public Bundle.ComponentEntity getComponent(Bundle bundle, String componentId, ApiParam... params) throws IOException, LognexApiException {
        return getComponent(bundle.getId(), componentId, params);
    }

    @ApiEndpoint
    public Bundle.ComponentEntity getComponent(Bundle bundle, Bundle.ComponentEntity component, ApiParam... params) throws IOException, LognexApiException {
        return getComponent(bundle, component.getId(), params);
    }

    @ApiEndpoint
    public void deleteComponent(String bundleId, String componentId) throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api(), path() + bundleId + "/components/" + componentId).
                delete();
    }

    @ApiEndpoint
    public void deleteComponent(Bundle entity, String componentId) throws IOException, LognexApiException {
        deleteComponent(entity.getId(), componentId);
    }

    @ApiEndpoint
    public void deleteComponent(Bundle entity, Bundle.ComponentEntity component) throws IOException, LognexApiException {
        deleteComponent(entity, component.getId());
    }
}
