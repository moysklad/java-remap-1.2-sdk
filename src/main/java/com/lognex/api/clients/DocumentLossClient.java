package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.LossDocumentEntity;

public final class DocumentLossClient
        extends ApiClient
        implements
        GetListEndpoint<LossDocumentEntity>,
        PostEndpoint<LossDocumentEntity> {

    public DocumentLossClient(LognexApi api) {
        super(api, "/entity/loss/", LossDocumentEntity.class);
    }
}
