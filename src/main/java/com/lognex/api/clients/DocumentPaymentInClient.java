package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.GetMetadataEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.PaymentInDocumentEntity;
import com.lognex.api.responses.DocumentMetadataStatesListResponse;

public final class DocumentPaymentInClient
        extends ApiClient
        implements
        GetListEndpoint<PaymentInDocumentEntity>,
        GetMetadataEndpoint<DocumentMetadataStatesListResponse>,
        PostEndpoint<PaymentInDocumentEntity> {

    public DocumentPaymentInClient(LognexApi api) {
        super(api, "/entity/paymentin/", PaymentInDocumentEntity.class, DocumentMetadataStatesListResponse.class);
    }
}
