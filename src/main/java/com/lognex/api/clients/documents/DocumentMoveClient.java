package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.MoveDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentMoveClient
        extends ApiClient
        implements
        GetListEndpoint<MoveDocumentEntity>,
        PostEndpoint<MoveDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<MoveDocumentEntity>,
        GetByIdEndpoint<MoveDocumentEntity>,
        PutByIdEndpoint<MoveDocumentEntity> {

    public DocumentMoveClient(LognexApi api) {
        super(api, "/entity/move/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return MoveDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
