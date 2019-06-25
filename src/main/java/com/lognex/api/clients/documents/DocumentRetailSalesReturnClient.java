package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailSalesReturnEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentRetailSalesReturnClient
        extends ApiClient
        implements
        GetListEndpoint<RetailSalesReturnEntity>,
        PostEndpoint<RetailSalesReturnEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        ExportEndpoint {

    public DocumentRetailSalesReturnClient(LognexApi api) {
        super(api, "/entity/retailsalesreturn/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailSalesReturnEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
