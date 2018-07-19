package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.PurchaseOrderDocumentEntity;

public final class DocumentPurchaseOrderClient
        extends ApiClient
        implements
        GetListEndpoint<PurchaseOrderDocumentEntity>,
        PostEndpoint<PurchaseOrderDocumentEntity> {

    public DocumentPurchaseOrderClient(LognexApi api) {
        super(api, "/entity/purchaseorder/", PurchaseOrderDocumentEntity.class);
    }
}
