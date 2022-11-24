package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.SystemCurrency;

public final class SystemCurrencyClient
        extends EntityClientBase
        implements
        GetListEndpoint<SystemCurrency>,
        PostEndpoint<SystemCurrency>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<SystemCurrency>,
        PutByIdEndpoint<SystemCurrency>,
        MassCreateUpdateDeleteEndpoint<SystemCurrency> {

    public SystemCurrencyClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/currency/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return SystemCurrency.class;
    }
}
