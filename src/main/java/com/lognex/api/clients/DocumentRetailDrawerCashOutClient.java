package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.RetailDrawerCashOutEntity;

public final class DocumentRetailDrawerCashOutClient
        extends ApiClient
        implements
        GetListEndpoint<RetailDrawerCashOutEntity>,
        PostEndpoint<RetailDrawerCashOutEntity>,
        DeleteByIdEndpoint {

    public DocumentRetailDrawerCashOutClient(LognexApi api) {
        super(api, "/entity/retaildrawercashout/", RetailDrawerCashOutEntity.class);
    }
}