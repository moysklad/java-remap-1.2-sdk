package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.InternalOrderDocumentEntity;

public final class DocumentInternalOrderClient
        extends ApiClient
        implements
        GetListEndpoint<InternalOrderDocumentEntity>,
        PostEndpoint<InternalOrderDocumentEntity> {

    public DocumentInternalOrderClient(LognexApi api) {
        super(api, "/entity/internalorder/", InternalOrderDocumentEntity.class);
    }
}
