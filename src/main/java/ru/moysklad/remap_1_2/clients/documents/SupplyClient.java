package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.Supply;
import ru.moysklad.remap_1_2.entities.documents.positions.SupplyDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class SupplyClient
        extends EntityClientBase
        implements
        GetListEndpoint<Supply>,
        PostEndpoint<Supply>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        DocumentNewEndpoint<Supply>,
        GetByIdEndpoint<Supply>,
        PutByIdEndpoint<Supply>,
        MassCreateUpdateDeleteEndpoint<Supply>,
        DocumentPositionsEndpoint<SupplyDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<Supply> {

    public SupplyClient(ApiClient api) {
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

    @Override
    public Class<SupplyDocumentPosition> documentPositionClass() {
        return SupplyDocumentPosition.class;
    }
}
