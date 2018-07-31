package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.RetailSalesReturnEntity;

public final class DocumentRetailSalesReturnClient
        extends ApiClient
        implements
        GetListEndpoint<RetailSalesReturnEntity>,
        PostEndpoint<RetailSalesReturnEntity>,
        DeleteByIdEndpoint {

    public DocumentRetailSalesReturnClient(LognexApi api) {
        super(api, "/entity/retailsalesreturn/", RetailSalesReturnEntity.class);
    }
}
