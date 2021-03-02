package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.discounts.AccumulationDiscount;

public final class AccumulationDiscountClient extends EntityClientBase
        implements
        GetListEndpoint<AccumulationDiscount>,
        GetByIdEndpoint<AccumulationDiscount>,
        PostEndpoint<AccumulationDiscount>,
        PutByIdEndpoint<AccumulationDiscount>,
        DeleteByIdEndpoint {

    public AccumulationDiscountClient(ApiClient api) {
        super(api, "/entity/accumulationdiscount/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return AccumulationDiscount.class;
    }
}
