package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.InternalOrderDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentInternalOrderClient
        extends ApiClient
        implements
        GetListEndpoint<InternalOrderDocumentEntity>,
        PostEndpoint<InternalOrderDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<InternalOrderDocumentEntity>,
        GetByIdEndpoint<InternalOrderDocumentEntity>,
        PutByIdEndpoint<InternalOrderDocumentEntity>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public DocumentInternalOrderClient(LognexApi api) {
        super(api, "/entity/internalorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return InternalOrderDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
