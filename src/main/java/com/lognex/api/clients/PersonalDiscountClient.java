package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.discounts.PersonalDiscount;

public final class PersonalDiscountClient extends EntityClientBase
        implements GetListEndpoint<PersonalDiscount>,
        GetByIdEndpoint<PersonalDiscount>,
        PostEndpoint<PersonalDiscount>,
        PutByIdEndpoint<PersonalDiscount>,
        DeleteByIdEndpoint {

    public PersonalDiscountClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/personaldiscount/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PersonalDiscount.class;
    }
}
