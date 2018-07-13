package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.PurchaseReturnDocumentEntity;

public class DocumentPurchaseReturnClient
        extends ApiClient
        implements
        GetListEndpoint<PurchaseReturnDocumentEntity>,
        PostEndpoint<PurchaseReturnDocumentEntity> {

    public DocumentPurchaseReturnClient(LognexApi api) {
        super(api, "/entity/purchasereturn/", PurchaseReturnDocumentEntity.class);
    }
}
