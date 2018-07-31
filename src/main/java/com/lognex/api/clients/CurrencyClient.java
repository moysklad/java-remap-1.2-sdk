package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.CurrencyEntity;

public final class CurrencyClient
        extends ApiClient
        implements
        GetListEndpoint<CurrencyEntity>,
        PostEndpoint<CurrencyEntity> {

    public CurrencyClient(LognexApi api) {
        super(api, "/entity/currency/", CurrencyEntity.class);
    }
}
