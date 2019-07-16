package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.Currency;
import com.lognex.api.entities.MetaEntity;

public final class CurrencyClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<Currency>,
        PostEndpoint<Currency>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<Currency>,
        PutByIdEndpoint<Currency> {

    public CurrencyClientEntity(com.lognex.api.ApiClient api) {
        super(api, "/entity/currency/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Currency.class;
    }
}
