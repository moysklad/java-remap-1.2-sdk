package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.Inventory;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentInventoryClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<Inventory>,
        PostEndpoint<Inventory>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Inventory>,
        PutByIdEndpoint<Inventory>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public DocumentInventoryClientEntity(ApiClient api) {
        super(api, "/entity/inventory/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Inventory.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
