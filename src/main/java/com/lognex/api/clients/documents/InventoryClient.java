package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.Inventory;
import com.lognex.api.entities.documents.positions.InventoryDocumentPosition;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class InventoryClient
        extends EntityClientBase
        implements
        GetListEndpoint<Inventory>,
        PostEndpoint<Inventory>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Inventory>,
        PutByIdEndpoint<Inventory>,
        MassCreateUpdateDeleteEndpoint<Inventory>,
        DocumentPositionsEndpoint<InventoryDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public InventoryClient(ApiClient api) {
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

    @Override
    public Class<InventoryDocumentPosition> documentPositionClass() {
        return InventoryDocumentPosition.class;
    }
}
