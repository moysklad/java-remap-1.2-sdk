package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.CurrencyEntity;
import com.lognex.api.entities.MetaEntity;

public final class CurrencyClient
        extends ApiClient
        implements
        GetListEndpoint<CurrencyEntity>,
        PostEndpoint<CurrencyEntity>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<CurrencyEntity>,
        PutByIdEndpoint<CurrencyEntity> {

    public CurrencyClient(LognexApi api) {
        super(api, "/entity/currency/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CurrencyEntity.class;
    }
}
