package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.Cashier;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.RetailStore;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.IOException;

public final class RetailStoreClient
        extends EntityClientBase
        implements
        GetListEndpoint<RetailStore>,
        PostEndpoint<RetailStore>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<RetailStore>,
        PutByIdEndpoint<RetailStore>,
        MassCreateUpdateDeleteEndpoint<RetailStore> {

    public RetailStoreClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/retailstore/");
    }

    @ApiEndpoint
    public ListEntity<Cashier> getCashiers(String retailStoreId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + retailStoreId + "/cashiers").
                apiParams(params).
                list(Cashier.class);
    }

    @ApiEndpoint
    public ListEntity<Cashier> getCashiers(MetaEntity retailStore, ApiParam... params) throws IOException, ApiClientException {
        return getCashiers(retailStore.getId(), params);
    }

    @ApiEndpoint
    public Cashier getCashier(String retailStoreId, String cashierId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + retailStoreId + "/cashiers/" + cashierId).
                apiParams(params).
                get(Cashier.class);
    }

    @ApiEndpoint
    public Cashier getCashier(String retailStoreId, Cashier cashier, ApiParam... params) throws IOException, ApiClientException {
        return getCashier(retailStoreId, cashier.getId());
    }

    @ApiEndpoint
    public Cashier getCashier(RetailStore retailStore, String cashierId, ApiParam... params) throws IOException, ApiClientException {
        return getCashier(retailStore.getId(), cashierId);
    }

    @ApiEndpoint
    public Cashier getCashier(RetailStore retailStore, Cashier cashier, ApiParam... params) throws IOException, ApiClientException {
        return getCashier(retailStore.getId(), cashier.getId());
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailStore.class;
    }
}
