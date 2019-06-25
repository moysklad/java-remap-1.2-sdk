package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.ProcessingOrderDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentProcessingOrderClient
        extends ApiClient
        implements
        GetListEndpoint<ProcessingOrderDocumentEntity>,
        PostEndpoint<ProcessingOrderDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<ProcessingOrderDocumentEntity>,
        GetByIdEndpoint<ProcessingOrderDocumentEntity>,
        PutByIdEndpoint<ProcessingOrderDocumentEntity>,
        ExportEndpoint {

    public DocumentProcessingOrderClient(LognexApi api) {
        super(api, "/entity/processingorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingOrderDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
