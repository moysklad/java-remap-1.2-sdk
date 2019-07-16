package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailDrawerCashOut;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentRetailDrawerCashOutClient
        extends com.lognex.api.clients.ApiClient
        implements
        GetListEndpoint<RetailDrawerCashOut>,
        PostEndpoint<RetailDrawerCashOut>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        ExportEndpoint {

    public DocumentRetailDrawerCashOutClient(ApiClient api) {
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