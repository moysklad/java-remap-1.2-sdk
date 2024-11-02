package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.PurchaseOrder;
import ru.moysklad.remap_1_2.entities.documents.positions.PurchaseOrderDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class PurchaseOrderClient
        extends EntityClientBase
        implements
        GetListEndpoint<PurchaseOrder>,
        PostEndpoint<PurchaseOrder>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        DocumentNewEndpoint<PurchaseOrder>,
        GetByIdEndpoint<PurchaseOrder>,
        PutByIdEndpoint<PurchaseOrder>,
        MassCreateUpdateDeleteEndpoint<PurchaseOrder>,
        DocumentPositionsEndpoint<PurchaseOrderDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<PurchaseOrder> {

    public PurchaseOrderClient(ApiClient api) {
        super(api, "/entity/purchaseorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PurchaseOrder.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }

    @Override
    public Class<PurchaseOrderDocumentPosition> documentPositionClass() {
        return PurchaseOrderDocumentPosition.class;
    }
}
