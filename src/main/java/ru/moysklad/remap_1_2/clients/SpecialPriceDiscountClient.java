package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.discounts.SpecialPriceDiscount;

public final class SpecialPriceDiscountClient extends EntityClientBase
        implements
        GetListEndpoint<SpecialPriceDiscount>,
        GetByIdEndpoint<SpecialPriceDiscount>,
        PostEndpoint<SpecialPriceDiscount>,
        PutByIdEndpoint<SpecialPriceDiscount>,
        DeleteByIdEndpoint {
    public SpecialPriceDiscountClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/specialpricediscount/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return SpecialPriceDiscount.class;
    }
}
