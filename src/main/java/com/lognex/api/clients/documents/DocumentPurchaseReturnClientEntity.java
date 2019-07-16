package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.PurchaseReturn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentPurchaseReturnClientEntity
        extends EntityApiClient
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

    public DocumentPurchaseReturnClientEntity(ApiClient api) {
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
