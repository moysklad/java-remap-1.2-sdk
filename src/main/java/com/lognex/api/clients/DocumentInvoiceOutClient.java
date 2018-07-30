package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.InvoiceOutDocumentEntity;

public final class DocumentInvoiceOutClient
        extends ApiClient
        implements
        GetListEndpoint<InvoiceOutDocumentEntity>,
        PostEndpoint<InvoiceOutDocumentEntity> {

    public DocumentInvoiceOutClient(LognexApi api) {
        super(api, "/entity/invoiceout/", InvoiceOutDocumentEntity.class);
    }
}
