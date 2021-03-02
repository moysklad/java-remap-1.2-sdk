package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.Country;
import ru.moysklad.remap_1_2.entities.MetaEntity;

public final class CountryClient
        extends EntityClientBase
        implements
        GetListEndpoint<Country>,
        PostEndpoint<Country>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<Country>,
        PutByIdEndpoint<Country>,
        MassCreateUpdateDeleteEndpoint<Country> {

    public CountryClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/country/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Country.class;
    }
}
