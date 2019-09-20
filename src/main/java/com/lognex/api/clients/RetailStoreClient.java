package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RetailStore;

public final class RetailStoreClient
        extends EntityClientBase
        implements
        GetListEndpoint<RetailStore>,
        PostEndpoint<RetailStore>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<RetailStore>,
        PutByIdEndpoint<RetailStore> {

    public RetailStoreClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/retailstore/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailStore.class;
    }
}
