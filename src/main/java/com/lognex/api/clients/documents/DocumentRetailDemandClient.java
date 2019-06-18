package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailDemandDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentRetailDemandClient
        extends ApiClient
        implements
        GetListEndpoint<RetailDemandDocumentEntity>,
        PostEndpoint<RetailDemandDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<RetailDemandDocumentEntity>,
        PutByIdEndpoint<RetailDemandDocumentEntity>,
        ExportEndpoint {

    public DocumentRetailDemandClient(LognexApi api) {
        super(api, "/entity/retaildemand/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDemandDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
