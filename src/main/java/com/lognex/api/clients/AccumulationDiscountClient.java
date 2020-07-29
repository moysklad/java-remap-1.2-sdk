package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.discounts.AccumulationDiscount;

public final class AccumulationDiscountClient extends EntityClientBase
        implements
        GetListEndpoint<AccumulationDiscount>,
        GetByIdEndpoint<AccumulationDiscount>,
        PostEndpoint<AccumulationDiscount>,
        PutByIdEndpoint<AccumulationDiscount>,
        DeleteByIdEndpoint {

    public AccumulationDiscountClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/accumulationdiscount/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return AccumulationDiscount.class;
    }
}
