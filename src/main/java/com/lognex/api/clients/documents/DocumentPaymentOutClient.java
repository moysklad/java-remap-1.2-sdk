package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.PaymentOutDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentPaymentOutClient
        extends ApiClient
        implements
        GetListEndpoint<PaymentOutDocumentEntity>,
        PostEndpoint<PaymentOutDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<PaymentOutDocumentEntity>,
        PutByIdEndpoint<PaymentOutDocumentEntity> {

    public DocumentPaymentOutClient(LognexApi api) {
        super(api, "/entity/paymentout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PaymentOutDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;

    }
}
