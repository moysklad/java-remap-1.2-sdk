package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RetailStoreEntity;

public final class RetailStoreClient
        extends ApiClient
        implements GetListEndpoint<RetailStoreEntity>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<RetailStoreEntity> {

    public RetailStoreClient(LognexApi api) {
        super(api, "/entity/retailstore/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailStoreEntity.class;
    }
}
