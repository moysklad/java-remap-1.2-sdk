package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.GetMetadataEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.CashInDocumentEntity;
import com.lognex.api.responses.DocumentMetadataStatesListResponse;

public final class DocumentCashInClient
        extends ApiClient
        implements
        GetListEndpoint<CashInDocumentEntity>,
        PostEndpoint<CashInDocumentEntity>,
        GetMetadataEndpoint<DocumentMetadataStatesListResponse>,
        DeleteByIdEndpoint {

    public DocumentCashInClient(LognexApi api) {
        super(api, "/entity/cashin/", CashInDocumentEntity.class, DocumentMetadataStatesListResponse.class);
    }
}
