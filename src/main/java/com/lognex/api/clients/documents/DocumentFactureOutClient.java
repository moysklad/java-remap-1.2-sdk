package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.FactureOutDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentFactureOutClient
        extends ApiClient
        implements
        GetListEndpoint<FactureOutDocumentEntity>,
        PostEndpoint<FactureOutDocumentEntity>,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<FactureOutDocumentEntity>,
        GetByIdEndpoint<FactureOutDocumentEntity>,
        PutByIdEndpoint<FactureOutDocumentEntity>,
        DeleteByIdEndpoint,
        ExportEndpoint {

    public DocumentFactureOutClient(LognexApi api) {
        super(api, "/entity/factureout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return FactureOutDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
