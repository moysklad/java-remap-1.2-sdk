package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.discounts.PersonalDiscount;

public final class PersonalDiscountClient extends EntityClientBase
        implements GetListEndpoint<PersonalDiscount>,
        GetByIdEndpoint<PersonalDiscount>,
        PostEndpoint<PersonalDiscount>,
        PutByIdEndpoint<PersonalDiscount>,
        DeleteByIdEndpoint {

    public PersonalDiscountClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/personaldiscount/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PersonalDiscount.class;
    }
}
