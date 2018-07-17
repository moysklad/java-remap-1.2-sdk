package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.MoveDocumentEntity;

public final class DocumentMoveClient
        extends ApiClient
        implements
        GetListEndpoint<MoveDocumentEntity>,
        PostEndpoint<MoveDocumentEntity> {

    public DocumentMoveClient(LognexApi api) {
        super(api, "/entity/move/", MoveDocumentEntity.class);
    }
}
