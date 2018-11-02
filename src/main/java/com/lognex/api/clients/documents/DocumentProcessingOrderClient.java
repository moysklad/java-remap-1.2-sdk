package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.DocumentMetadataEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.MetadataAttributeEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.ProcessingOrderDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentProcessingOrderClient
        extends ApiClient
        implements
        GetListEndpoint<ProcessingOrderDocumentEntity>,
        PostEndpoint<ProcessingOrderDocumentEntity>,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint {

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
