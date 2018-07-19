package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.StoreEntity;

public final class StoreClient
        extends ApiClient
        implements
        GetListEndpoint<StoreEntity>,
        PostEndpoint<StoreEntity> {

    public StoreClient(LognexApi api) {
        super(api, "/entity/store", StoreEntity.class);
    }
}
