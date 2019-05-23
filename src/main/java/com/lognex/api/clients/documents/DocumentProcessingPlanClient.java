package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.ProcessingPlanDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentProcessingPlanClient
        extends ApiClient
        implements
        GetListEndpoint<ProcessingPlanDocumentEntity>,
        PostEndpoint<ProcessingPlanDocumentEntity>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<ProcessingPlanDocumentEntity>,
        PutByIdEndpoint<ProcessingPlanDocumentEntity> {

    public DocumentProcessingPlanClient(LognexApi api) {
        super(api, "/entity/processingplan/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingPlanDocumentEntity.class;
    }
}
