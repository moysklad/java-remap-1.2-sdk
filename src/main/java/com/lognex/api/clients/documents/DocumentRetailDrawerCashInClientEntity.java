package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailDrawerCashIn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentRetailDrawerCashInClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<RetailDrawerCashIn>,
        PostEndpoint<RetailDrawerCashIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        ExportEndpoint {

    public DocumentRetailDrawerCashInClientEntity(ApiClient api) {
        super(api, "/entity/retaildrawercashin/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDrawerCashIn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
