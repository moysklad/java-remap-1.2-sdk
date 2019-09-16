package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
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
        GetByIdEndpoint<RetailStore> {

    public RetailStoreClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/retailstore/");
    }

    public ListEntity<Cashier> getCashiers(String retailStoreId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + retailStoreId + "/cashiers").
                apiParams(params).
                list(Cashier.class);
    }

    public ListEntity<Cashier> getCashiers(MetaEntity retailStore, ApiParam... params) throws IOException, ApiClientException {
        return getCashiers(retailStore.getId(), params);
    }

    public Cashier getCashier(String retailStoreId, String cashierId) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + retailStoreId + "/cashiers/" + cashierId).
                get(Cashier.class);
    }

    public Cashier getCashier(String retailStoreId, Cashier cashier) throws IOException, ApiClientException {
        return getCashier(retailStoreId, cashier.getId());
    }

    public Cashier getCashier(RetailStore retailStore, String cashierId) throws IOException, ApiClientException {
        return getCashier(retailStore.getId(), cashierId);
    }

    public Cashier getCashier(RetailStore retailStore, Cashier cashier) throws IOException, ApiClientException {
        return getCashier(retailStore.getId(), cashier.getId());
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailStore.class;
    }
}
