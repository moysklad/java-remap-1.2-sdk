package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteEndpoint;
import com.lognex.api.clients.endpoints.GetEndpoint;
import com.lognex.api.clients.endpoints.PutEndpoint;
import com.lognex.api.entities.products.VariantEntity;

public final class VariantByIdClient
        extends ApiClient
        implements
        GetEndpoint<VariantEntity>,
        DeleteEndpoint,
        PutEndpoint<VariantEntity> {

    public VariantByIdClient(LognexApi api, String id) {
        super(api, "/entity/variant/" + id, VariantEntity.class);
    }
}
