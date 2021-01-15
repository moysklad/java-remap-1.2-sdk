package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.ExpenseItem;
import ru.moysklad.remap_1_2.entities.MetaEntity;

public final class ExpenseItemClient
        extends EntityClientBase
        implements
        GetListEndpoint<ExpenseItem>,
        PostEndpoint<ExpenseItem>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<ExpenseItem>,
        PutByIdEndpoint<ExpenseItem>,
        MassCreateUpdateDeleteEndpoint<ExpenseItem> {

    public ExpenseItemClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/expenseitem/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ExpenseItem.class;
    }
}
