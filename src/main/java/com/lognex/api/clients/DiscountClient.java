package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.DiscountEntity;

public final class DiscountClient
        extends ApiClient
        implements GetListEndpoint<DiscountEntity> {

    public DiscountClient(LognexApi api) {
        super(api, "/entity/discount", DiscountEntity.class);
    }
}
