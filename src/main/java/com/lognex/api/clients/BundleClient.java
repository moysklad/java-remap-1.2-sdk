package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.BundleEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeResponse;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;

public final class BundleClient
        extends ApiClient
        implements
        GetListEndpoint<BundleEntity>,
        PostEndpoint<BundleEntity>,
        MetadataEndpoint<MetadataAttributeResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<BundleEntity>,
        PutByIdEndpoint<BundleEntity>,
        DeleteByIdEndpoint {

    public BundleClient(LognexApi api) {
        super(api, "/entity/bundle/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return BundleEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeResponse.class;
    }

    @ApiEndpoint
    public ListEntity<BundleEntity.ComponentEntity> getComponents(String bundleId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + bundleId + "/components").
                apiParams(params).
                list(BundleEntity.ComponentEntity.class);
    }

    @ApiEndpoint
    public ListEntity<BundleEntity.ComponentEntity> getComponents(BundleEntity bundle, ApiParam... params) throws IOException, LognexApiException {
        return getComponents(bundle.getId(), params);
    }

    @ApiEndpoint
    public BundleEntity.ComponentEntity getComponent(String bundleId, String componentId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + bundleId + "/components/" + componentId).
                apiParams(params).
                get(BundleEntity.ComponentEntity.class);
    }

    @ApiEndpoint
    public BundleEntity.ComponentEntity getComponent(BundleEntity bundle, String componentId, ApiParam... params) throws IOException, LognexApiException {
        return getComponent(bundle.getId(), componentId, params);
    }

    @ApiEndpoint
    public BundleEntity.ComponentEntity getComponent(BundleEntity bundle, BundleEntity.ComponentEntity component, ApiParam... params) throws IOException, LognexApiException {
        return getComponent(bundle, component.getId(), params);
    }

    @ApiEndpoint
    public void deleteComponent(String bundleId, String componentId) throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api(), path() + bundleId + "/components/" + componentId).
                delete();
    }

    @ApiEndpoint
    public void deleteComponent(BundleEntity entity, String componentId) throws IOException, LognexApiException {
        deleteComponent(entity.getId(), componentId);
    }

    @ApiEndpoint
    public void deleteComponent(BundleEntity entity, BundleEntity.ComponentEntity component) throws IOException, LognexApiException {
        deleteComponent(entity, component.getId());
    }
}
