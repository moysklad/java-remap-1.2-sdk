package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.Prepayment;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class PrepaymentClient
        extends EntityClientBase
        implements
        GetListEndpoint<Prepayment>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<Prepayment>,
        GetByIdEndpoint<Prepayment>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public PrepaymentClient(ApiClient api) {
        super(api, "/entity/prepayment/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Prepayment.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
