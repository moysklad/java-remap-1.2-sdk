package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.PaymentOut;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class PaymentOutClient
        extends EntityClientBase
        implements
        GetListEndpoint<PaymentOut>,
        PostEndpoint<PaymentOut>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<PaymentOut>,
        GetByIdEndpoint<PaymentOut>,
        PutByIdEndpoint<PaymentOut>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public PaymentOutClient(ApiClient api) {
        super(api, "/entity/paymentout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PaymentOut.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;

    }
}
