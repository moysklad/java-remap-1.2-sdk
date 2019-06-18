package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.FactureInDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentFactureInClient
        extends ApiClient
        implements
        GetListEndpoint<FactureInDocumentEntity>,
        PostEndpoint<FactureInDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<FactureInDocumentEntity>,
        GetByIdEndpoint<FactureInDocumentEntity>,
        PutByIdEndpoint<FactureInDocumentEntity>,
        ExportEndpoint {

    public DocumentFactureInClient(LognexApi api) {
        super(api, "/entity/facturein/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return FactureInDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
