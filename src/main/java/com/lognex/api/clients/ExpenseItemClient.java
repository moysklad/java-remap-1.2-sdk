package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.ExpenseItem;
import com.lognex.api.entities.MetaEntity;

public final class ExpenseItemClient
        extends EntityClientBase
        implements
        GetListEndpoint<ExpenseItem>,
        PostEndpoint<ExpenseItem>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<ExpenseItem>,
        PutByIdEndpoint<ExpenseItem>,
        MassCreateUpdateDeleteEndpoint<ExpenseItem> {

    public ExpenseItemClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/expenseitem/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ExpenseItem.class;
    }
}
