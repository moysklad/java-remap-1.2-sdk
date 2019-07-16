package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.ExpenseItem;
import com.lognex.api.entities.MetaEntity;

public final class ExpenseItemClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<ExpenseItem>,
        PostEndpoint<ExpenseItem>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<ExpenseItem>,
        PutByIdEndpoint<ExpenseItem> {

    public ExpenseItemClientEntity(com.lognex.api.ApiClient api) {
        super(api, "/entity/expenseitem/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ExpenseItem.class;
    }
}
