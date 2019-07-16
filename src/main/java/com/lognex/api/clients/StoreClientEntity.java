package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Store;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;

public final class StoreClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<Store>,
        PostEndpoint<Store>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Store>,
        PutByIdEndpoint<Store> {

    public StoreClientEntity(ApiClient api) {
        super(api, "/entity/store/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Store.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedResponse.class;
    }
}
