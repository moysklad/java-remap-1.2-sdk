package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RetailStore;

public final class RetailStoreClientEntity
        extends EntityApiClient
        implements GetListEndpoint<RetailStore>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<RetailStore> {

    public RetailStoreClientEntity(com.lognex.api.ApiClient api) {
        super(api, "/entity/retailstore/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailStore.class;
    }
}
