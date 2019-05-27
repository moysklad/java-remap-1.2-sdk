package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.PurchaseReturnDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentPurchaseReturnClient
        extends ApiClient
        implements
        GetListEndpoint<PurchaseReturnDocumentEntity>,
        PostEndpoint<PurchaseReturnDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<PurchaseReturnDocumentEntity>,
        GetByIdEndpoint<PurchaseReturnDocumentEntity>,
        PutByIdEndpoint<PurchaseReturnDocumentEntity>,
        DocumentPositionsEndpoint {

    public DocumentPurchaseReturnClient(LognexApi api) {
        super(api, "/entity/purchasereturn/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PurchaseReturnDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
