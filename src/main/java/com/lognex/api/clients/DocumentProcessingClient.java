package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.ProcessingDocumentEntity;

public final class DocumentProcessingClient
        extends ApiClient
        implements
        GetListEndpoint<ProcessingDocumentEntity>,
        PostEndpoint<ProcessingDocumentEntity> {

    public DocumentProcessingClient(LognexApi api) {
        super(api, "/entity/processing", ProcessingDocumentEntity.class);
    }
}
