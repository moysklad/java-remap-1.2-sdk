package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.InventoryDocumentEntity;

public final class DocumentInventoryClient
        extends ApiClient
        implements
        GetListEndpoint<InventoryDocumentEntity>,
        PostEndpoint<InventoryDocumentEntity> {

    public DocumentInventoryClient(LognexApi api) {
        super(api, "/entity/inventory/", InventoryDocumentEntity.class);
    }
}
