package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.InternalOrder;
import ru.moysklad.remap_1_2.entities.documents.positions.InternalOrderDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class InternalOrderClient
        extends EntityClientBase
        implements
        GetListEndpoint<InternalOrder>,
        PostEndpoint<InternalOrder>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        DocumentNewEndpoint<InternalOrder>,
        GetByIdEndpoint<InternalOrder>,
        PutByIdEndpoint<InternalOrder>,
        MassCreateUpdateDeleteEndpoint<InternalOrder>,
        DocumentPositionsEndpoint<InternalOrderDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<InternalOrder> {

    public InternalOrderClient(ApiClient api) {
        super(api, "/entity/internalorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return InternalOrder.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }

    @Override
    public Class<InternalOrderDocumentPosition> documentPositionClass() {
        return InternalOrderDocumentPosition.class;
    }
}
