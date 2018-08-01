package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.InvoiceInDocumentEntity;

public final class DocumentInvoiceInClient
        extends ApiClient
        implements
        GetListEndpoint<InvoiceInDocumentEntity>,
        PostEndpoint<InvoiceInDocumentEntity> {

    public DocumentInvoiceInClient(LognexApi api) {
        super(api, "/entity/invoicein/", InvoiceInDocumentEntity.class);
    }
}
