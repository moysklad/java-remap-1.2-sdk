package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.Currency;
import ru.moysklad.remap_1_2.entities.MetaEntity;

public final class CurrencyClient
        extends EntityClientBase
        implements
        GetListEndpoint<Currency>,
        PostEndpoint<Currency>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<Currency>,
        PutByIdEndpoint<Currency>,
        MassCreateUpdateDeleteEndpoint<Currency> {

    public CurrencyClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/currency/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Currency.class;
    }
}
