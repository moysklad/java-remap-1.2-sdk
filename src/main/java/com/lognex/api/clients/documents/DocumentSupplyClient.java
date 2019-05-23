package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.SupplyDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentSupplyClient
        extends ApiClient
        implements
        GetListEndpoint<SupplyDocumentEntity>,
        PostEndpoint<SupplyDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<SupplyDocumentEntity>,
        PutByIdEndpoint<SupplyDocumentEntity> {

    public DocumentSupplyClient(LognexApi api) {
        super(api, "/entity/supply/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return SupplyDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
