package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.Country;
import com.lognex.api.entities.MetaEntity;

public final class CountryClient
        extends EntityClientBase
        implements
        GetListEndpoint<Country>,
        PostEndpoint<Country>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<Country>,
        PutByIdEndpoint<Country> {

    public CountryClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/country/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Country.class;
    }
}
