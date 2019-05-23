package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.CashInDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentCashInClient
        extends ApiClient
        implements
        GetListEndpoint<CashInDocumentEntity>,
        PostEndpoint<CashInDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<CashInDocumentEntity>,
        PutByIdEndpoint<CashInDocumentEntity> {

    public DocumentCashInClient(LognexApi api) {
        super(api, "/entity/cashin/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CashInDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
