package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailSalesReturn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentRetailSalesReturnClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<RetailSalesReturn>,
        PostEndpoint<RetailSalesReturn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        ExportEndpoint {

    public DocumentRetailSalesReturnClientEntity(ApiClient api) {
        super(api, "/entity/retailsalesreturn/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailSalesReturn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
