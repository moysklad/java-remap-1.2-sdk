package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.CashOut;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentCashOutClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<CashOut>,
        PostEndpoint<CashOut>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<CashOut>,
        GetByIdEndpoint<CashOut>,
        PutByIdEndpoint<CashOut>,
        ExportEndpoint {

    public DocumentCashOutClientEntity(ApiClient api) {
        super(api, "/entity/cashout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CashOut.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
