package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.PurchaseReturn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentPurchaseReturnClient
        extends com.lognex.api.clients.ApiClient
        implements
        GetListEndpoint<PurchaseReturn>,
        PostEndpoint<PurchaseReturn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<PurchaseReturn>,
        GetByIdEndpoint<PurchaseReturn>,
        PutByIdEndpoint<PurchaseReturn>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public DocumentPurchaseReturnClient(ApiClient api) {
        super(api, "/entity/purchasereturn/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PurchaseReturn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
