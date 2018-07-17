package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.ProcessingOrderDocumentEntity;

public final class DocumentProcessingOrderClient
        extends ApiClient
        implements
        GetListEndpoint<ProcessingOrderDocumentEntity>,
        PostEndpoint<ProcessingOrderDocumentEntity> {

    public DocumentProcessingOrderClient(LognexApi api) {
        super(api, "/entity/processingorder/", ProcessingOrderDocumentEntity.class);
    }
}
