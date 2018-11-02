package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.discounts.DiscountEntity;

public final class DiscountClient
        extends ApiClient
        implements GetListEndpoint<DiscountEntity> {

    public DiscountClient(LognexApi api) {
        super(api, "/entity/discount/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return DiscountEntity.class;
    }
}
