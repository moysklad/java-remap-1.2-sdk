package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.GetMetadataEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.CashOutDocumentEntity;
import com.lognex.api.responses.DocumentMetadataStatesListResponse;

public final class DocumentCashOutClient
        extends ApiClient
        implements
        GetListEndpoint<CashOutDocumentEntity>,
        GetMetadataEndpoint<DocumentMetadataStatesListResponse>,
        PostEndpoint<CashOutDocumentEntity> {

    public DocumentCashOutClient(LognexApi api) {
        super(api, "/entity/cashout/", CashOutDocumentEntity.class, DocumentMetadataStatesListResponse.class);
    }
}
