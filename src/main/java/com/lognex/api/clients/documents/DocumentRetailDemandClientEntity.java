package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailDemand;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentRetailDemandClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<RetailDemand>,
        PostEndpoint<RetailDemand>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<RetailDemand>,
        PutByIdEndpoint<RetailDemand>,
        ExportEndpoint {

    public DocumentRetailDemandClientEntity(ApiClient api) {
        super(api, "/entity/retaildemand/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDemand.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
