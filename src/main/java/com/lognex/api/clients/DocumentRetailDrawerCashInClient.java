package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.RetailDrawerCashInEntity;

public final class DocumentRetailDrawerCashInClient
        extends ApiClient
        implements
        GetListEndpoint<RetailDrawerCashInEntity>,
        PostEndpoint<RetailDrawerCashInEntity>,
        DeleteByIdEndpoint {

    public DocumentRetailDrawerCashInClient(LognexApi api) {
        super(api, "/entity/retaildrawercashin/", RetailDrawerCashInEntity.class);
    }
}
