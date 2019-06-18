package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.LossDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentLossClient
        extends ApiClient
        implements
        GetListEndpoint<LossDocumentEntity>,
        PostEndpoint<LossDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<LossDocumentEntity>,
        GetByIdEndpoint<LossDocumentEntity>,
        PutByIdEndpoint<LossDocumentEntity>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public DocumentLossClient(LognexApi api) {
        super(api, "/entity/loss/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return LossDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
