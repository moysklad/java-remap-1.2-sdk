package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.ProcessingOrder;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentProcessingOrderClient
        extends com.lognex.api.clients.ApiClient
        implements
        GetListEndpoint<ProcessingOrder>,
        PostEndpoint<ProcessingOrder>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<ProcessingOrder>,
        GetByIdEndpoint<ProcessingOrder>,
        PutByIdEndpoint<ProcessingOrder>,
        ExportEndpoint {

    public DocumentProcessingOrderClient(ApiClient api) {
        super(api, "/entity/processingorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingOrder.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
