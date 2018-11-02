package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.PaymentInDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentPaymentInClient
        extends ApiClient
        implements
        GetListEndpoint<PaymentInDocumentEntity>,
        PostEndpoint<PaymentInDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<PaymentInDocumentEntity>,
        GetByIdEndpoint<PaymentInDocumentEntity>,
        PutByIdEndpoint<PaymentInDocumentEntity> {

    public DocumentPaymentInClient(LognexApi api) {
        super(api, "/entity/paymentin/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PaymentInDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
