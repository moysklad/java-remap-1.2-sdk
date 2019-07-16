package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailDrawerCashOut;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentRetailDrawerCashOutClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<RetailDrawerCashOut>,
        PostEndpoint<RetailDrawerCashOut>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        ExportEndpoint {

    public DocumentRetailDrawerCashOutClientEntity(ApiClient api) {
        super(api, "/entity/retaildrawercashout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDrawerCashOut.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}