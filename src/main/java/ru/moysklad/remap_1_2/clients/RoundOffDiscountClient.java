package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.GetByIdEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.PutByIdEndpoint;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.discounts.RoundOffDiscount;

public final class RoundOffDiscountClient extends EntityClientBase
        implements
        GetByIdEndpoint<RoundOffDiscount>,
        PutByIdEndpoint<RoundOffDiscount> {

    public RoundOffDiscountClient(ApiClient api) {
        super(api, "/entity/discount/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RoundOffDiscount.class;
    }
}
