package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.Currency;
import com.lognex.api.entities.MetaEntity;

public final class CurrencyClient
        extends EntityClientBase
        implements
        GetListEndpoint<Currency>,
        PostEndpoint<Currency>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<Currency>,
        PutByIdEndpoint<Currency>,
        MassCreateUpdateDeleteEndpoint<Currency> {

    public CurrencyClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/currency/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Currency.class;
    }
}
