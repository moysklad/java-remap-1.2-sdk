package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.Processing;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentProcessingClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<Processing>,
        PostEndpoint<Processing>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Processing>,
        PutByIdEndpoint<Processing>,
        ExportEndpoint {

    public DocumentProcessingClientEntity(ApiClient api) {
        super(api, "/entity/processing/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Processing.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
