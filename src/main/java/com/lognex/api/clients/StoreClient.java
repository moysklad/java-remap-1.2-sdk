package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;

public final class StoreClient
        extends ApiClient
        implements
        GetListEndpoint<StoreEntity>,
        PostEndpoint<StoreEntity>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<StoreEntity>,
        PutByIdEndpoint<StoreEntity> {

    public StoreClient(LognexApi api) {
        super(api, "/entity/store/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return StoreEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedResponse.class;
    }
}
