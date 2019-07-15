package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RetailStore;

public final class RetailStoreClient
        extends com.lognex.api.clients.ApiClient
        implements GetListEndpoint<RetailStore>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<RetailStore> {

    public RetailStoreClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/retailstore/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailStore.class;
    }
}
