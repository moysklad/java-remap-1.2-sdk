package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.products.VariantEntity;

public final class VariantClient
        extends ApiClient
        implements
        GetListEndpoint<VariantEntity>,
        PostEndpoint<VariantEntity> {

    public VariantClient(LognexApi api) {
        super(api, "/entity/variant/", VariantEntity.class);
    }
}