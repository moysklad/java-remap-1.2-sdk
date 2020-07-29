package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.discounts.SpecialPriceDiscount;

public final class SpecialPriceDiscountClient extends EntityClientBase
        implements
        GetListEndpoint<SpecialPriceDiscount>,
        GetByIdEndpoint<SpecialPriceDiscount>,
        PostEndpoint<SpecialPriceDiscount>,
        PutByIdEndpoint<SpecialPriceDiscount>,
        DeleteByIdEndpoint {
    public SpecialPriceDiscountClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/specialpricediscount/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return SpecialPriceDiscount.class;
    }
}
