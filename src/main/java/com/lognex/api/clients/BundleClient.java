package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.products.BundleEntity;

public final class BundleClient
        extends ApiClient
        implements
        GetListEndpoint<BundleEntity>,
        PostEndpoint<BundleEntity> {

    public BundleClient(LognexApi api) {
        super(api, "/entity/bundle/", BundleEntity.class);
    }
}
