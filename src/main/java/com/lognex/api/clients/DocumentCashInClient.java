package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.CashInDocumentEntity;

public class DocumentCashInClient
        extends ApiClient
        implements
        GetListEndpoint<CashInDocumentEntity>,
        PostEndpoint<CashInDocumentEntity>,
        DeleteByIdEndpoint {

    public DocumentCashInClient(LognexApi api) {
        super(api, "/entity/cashin/", CashInDocumentEntity.class);
    }
}
