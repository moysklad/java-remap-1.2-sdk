package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.GetMetadataEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.PaymentOutDocumentEntity;
import com.lognex.api.responses.DocumentMetadataStatesListResponse;

public final class DocumentPaymentOutClient
        extends ApiClient
        implements
        GetListEndpoint<PaymentOutDocumentEntity>,
        GetMetadataEndpoint<DocumentMetadataStatesListResponse>,
        PostEndpoint<PaymentOutDocumentEntity> {

    public DocumentPaymentOutClient(LognexApi api) {
        super(api, "/entity/paymentout/", PaymentOutDocumentEntity.class, DocumentMetadataStatesListResponse.class);
    }
}
