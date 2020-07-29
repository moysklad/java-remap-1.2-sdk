package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.PutByIdEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.discounts.RoundOffDiscount;

public final class RoundOffDiscountClient extends EntityClientBase
        implements
        GetByIdEndpoint<RoundOffDiscount>,
        PutByIdEndpoint<RoundOffDiscount> {

    public RoundOffDiscountClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/discount/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RoundOffDiscount.class;
    }
}
