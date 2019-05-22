package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.ExpenseItemEntity;
import com.lognex.api.entities.MetaEntity;

public final class ExpenseItemClient
        extends ApiClient
        implements
        GetListEndpoint<ExpenseItemEntity>,
        PostEndpoint<ExpenseItemEntity>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<ExpenseItemEntity>,
        PutByIdEndpoint<ExpenseItemEntity> {

    public ExpenseItemClient(LognexApi api) {
        super(api, "/entity/expenseitem/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ExpenseItemEntity.class;
    }
}
