package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailDrawerCashOutEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentRetailDrawerCashOutClient
        extends ApiClient
        implements
        GetListEndpoint<RetailDrawerCashOutEntity>,
        PostEndpoint<RetailDrawerCashOutEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        ExportEndpoint {

    public DocumentRetailDrawerCashOutClient(LognexApi api) {
        super(api, "/entity/retaildrawercashout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDrawerCashOutEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}