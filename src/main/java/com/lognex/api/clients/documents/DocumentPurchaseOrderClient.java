package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.PurchaseOrderDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentPurchaseOrderClient
        extends ApiClient
        implements
        GetListEndpoint<PurchaseOrderDocumentEntity>,
        PostEndpoint<PurchaseOrderDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<PurchaseOrderDocumentEntity>,
        GetByIdEndpoint<PurchaseOrderDocumentEntity>,
        PutByIdEndpoint<PurchaseOrderDocumentEntity>,
        DocumentPositionsEndpoint {

    public DocumentPurchaseOrderClient(LognexApi api) {
        super(api, "/entity/purchaseorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PurchaseOrderDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
