package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailDrawerCashInEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentRetailDrawerCashInClient
        extends ApiClient
        implements
        GetListEndpoint<RetailDrawerCashInEntity>,
        PostEndpoint<RetailDrawerCashInEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint {

    public DocumentRetailDrawerCashInClient(LognexApi api) {
        super(api, "/entity/retaildrawercashin/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDrawerCashInEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
