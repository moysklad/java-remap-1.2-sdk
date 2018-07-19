package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.ProcessingPlanDocumentEntity;

public final class DocumentProcessingPlanClient
        extends ApiClient
        implements
        GetListEndpoint<ProcessingPlanDocumentEntity>,
        PostEndpoint<ProcessingPlanDocumentEntity> {

    public DocumentProcessingPlanClient(LognexApi api) {
        super(api, "/entity/processingplan/", ProcessingPlanDocumentEntity.class);
    }
}
