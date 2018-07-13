package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.EnterDocumentEntity;

public final class DocumentEnterClient
        extends ApiClient
        implements
        GetListEndpoint<EnterDocumentEntity>,
        PostEndpoint<EnterDocumentEntity> {

    public DocumentEnterClient(LognexApi api) {
        super(api, "/entity/enter", EnterDocumentEntity.class);
    }
}
