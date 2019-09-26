package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.ApiEndpoint;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PutByIdEndpoint;
import com.lognex.api.entities.Cashier;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RetailStore;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;

public final class RetailStoreClient
        extends EntityClientBase
        implements GetListEndpoint<RetailStore>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<RetailStore>,
        PutByIdEndpoint<RetailStore> {

    public RetailStoreClient(com.lognex.api.ApiClient api) {
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
