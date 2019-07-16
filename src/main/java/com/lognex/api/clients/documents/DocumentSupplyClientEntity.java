package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.Supply;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentSupplyClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<Supply>,
        PostEndpoint<Supply>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<Supply>,
        GetByIdEndpoint<Supply>,
        PutByIdEndpoint<Supply>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public DocumentSupplyClientEntity(ApiClient api) {
        super(api, "/entity/supply/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Supply.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
