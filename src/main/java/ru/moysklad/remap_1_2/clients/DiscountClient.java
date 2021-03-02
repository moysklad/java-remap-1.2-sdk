package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.GetListEndpoint;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.discounts.Discount;

public final class DiscountClient
        extends EntityClientBase
        implements GetListEndpoint<Discount> {

    public DiscountClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/discount/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Discount.class;
    }
}
