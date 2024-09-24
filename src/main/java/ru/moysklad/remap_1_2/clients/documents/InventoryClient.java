package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.Inventory;
import ru.moysklad.remap_1_2.entities.documents.positions.InventoryDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeOperationSharedStatesResponse;

public final class InventoryClient
        extends EntityClientBase
        implements
        GetListEndpoint<Inventory>,
        PostEndpoint<Inventory>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeOperationSharedStatesResponse>,
        MetadataAttributeOperationEndpoint,
        GetByIdEndpoint<Inventory>,
        PutByIdEndpoint<Inventory>,
        MassCreateUpdateDeleteEndpoint<Inventory>,
        DocumentPositionsEndpoint<InventoryDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<Inventory> {

    public InventoryClient(ApiClient api) {
        super(api, "/entity/inventory/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Inventory.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeOperationSharedStatesResponse.class;
    }

    @Override
    public Class<InventoryDocumentPosition> documentPositionClass() {
        return InventoryDocumentPosition.class;
    }
}
