package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.discounts.Discount;

public final class DiscountClient
        extends EntityClientBase
        implements GetListEndpoint<Discount> {

    public DiscountClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/discount/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Discount.class;
    }
}
