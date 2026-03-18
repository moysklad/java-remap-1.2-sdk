package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.EmissionOrder;
import ru.moysklad.remap_1_2.entities.documents.positions.EmissionOrderDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class EmissionOrderClient
        extends EntityClientBase
        implements
        GetListEndpoint<EmissionOrder>,
        PostEndpoint<EmissionOrder>,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        GetByIdEndpoint<EmissionOrder>,
        PutByIdEndpoint<EmissionOrder>,
        MassCreateUpdateEndpoint<EmissionOrder>,
        DocumentPositionsEndpoint<EmissionOrderDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public EmissionOrderClient(ApiClient api) {
        super(api, "/entity/emissionorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return EmissionOrder.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }

    @Override
    public Class<EmissionOrderDocumentPosition> documentPositionClass() {
        return EmissionOrderDocumentPosition.class;
    }
}
