package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.PaymentIn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class PaymentInClient
        extends EntityClientBase
        implements
        GetListEndpoint<PaymentIn>,
        PostEndpoint<PaymentIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<PaymentIn>,
        GetByIdEndpoint<PaymentIn>,
        PutByIdEndpoint<PaymentIn>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public PaymentInClient(ApiClient api) {
        super(api, "/entity/paymentin/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PaymentIn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
