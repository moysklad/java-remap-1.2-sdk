package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.PurchaseReturn;
import ru.moysklad.remap_1_2.entities.documents.positions.PurchaseReturnDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class PurchaseReturnClient
        extends EntityClientBase
        implements
        GetListEndpoint<PurchaseReturn>,
        PostEndpoint<PurchaseReturn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        DocumentNewEndpoint<PurchaseReturn>,
        GetByIdEndpoint<PurchaseReturn>,
        PutByIdEndpoint<PurchaseReturn>,
        MassCreateUpdateDeleteEndpoint<PurchaseReturn>,
        DocumentPositionsEndpoint<PurchaseReturnDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<PurchaseReturn> {

    public PurchaseReturnClient(ApiClient api) {
        super(api, "/entity/purchasereturn/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PurchaseReturn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }

    @Override
    public Class<PurchaseReturnDocumentPosition> documentPositionClass() {
        return PurchaseReturnDocumentPosition.class;
    }
}
