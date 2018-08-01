package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.ExpenseItemEntity;

public final class ExpenseItemClient
        extends ApiClient
        implements
        GetListEndpoint<ExpenseItemEntity>,
        PostEndpoint<ExpenseItemEntity> {

    public ExpenseItemClient(LognexApi api) {
        super(api, "/entity/expenseitem/", ExpenseItemEntity.class);
    }
}
