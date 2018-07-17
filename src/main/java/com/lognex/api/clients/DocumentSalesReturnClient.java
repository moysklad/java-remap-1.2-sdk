package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.SalesReturnDocumentEntity;

public final class DocumentSalesReturnClient
        extends ApiClient
        implements
        GetListEndpoint<SalesReturnDocumentEntity>,
        PostEndpoint<SalesReturnDocumentEntity> {

    public DocumentSalesReturnClient(LognexApi api) {
        super(api, "/entity/salesreturn/", SalesReturnDocumentEntity.class);
    }
}
