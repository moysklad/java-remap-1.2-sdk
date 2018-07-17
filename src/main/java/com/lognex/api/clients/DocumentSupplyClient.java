package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.SupplyDocumentEntity;

public final class DocumentSupplyClient
        extends ApiClient
        implements
        GetListEndpoint<SupplyDocumentEntity>,
        PostEndpoint<SupplyDocumentEntity> {

    public DocumentSupplyClient(LognexApi api) {
        super(api, "/entity/supply", SupplyDocumentEntity.class);
    }
}
