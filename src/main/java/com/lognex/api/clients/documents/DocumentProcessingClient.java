package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.ProcessingDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentProcessingClient
        extends ApiClient
        implements
        GetListEndpoint<ProcessingDocumentEntity>,
        PostEndpoint<ProcessingDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<ProcessingDocumentEntity>,
        PutByIdEndpoint<ProcessingDocumentEntity> {

    public DocumentProcessingClient(LognexApi api) {
        super(api, "/entity/processing/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
