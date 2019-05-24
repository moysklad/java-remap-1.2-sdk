package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.PricelistDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentPricelistClient
        extends ApiClient
        implements
        GetListEndpoint<PricelistDocumentEntity>,
        PostEndpoint<PricelistDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<PricelistDocumentEntity>,
        PutByIdEndpoint<PricelistDocumentEntity> {

    public DocumentPricelistClient(LognexApi api) {
        super(api, "/entity/pricelist/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PricelistDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
