package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.discounts.BonusProgramDiscount;

public final class BonusProgramClient
        extends EntityClientBase
        implements
        GetListEndpoint<BonusProgramDiscount>,
        GetByIdEndpoint<BonusProgramDiscount>,
        DeleteByIdEndpoint {

    public BonusProgramClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/bonusprogram/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return BonusProgramDiscount.class;
    }
}
