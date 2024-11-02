package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.Demand;
import ru.moysklad.remap_1_2.entities.documents.positions.DemandDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DemandClient
        extends EntityClientBase
        implements
        GetListEndpoint<Demand>,
        PostEndpoint<Demand>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        DocumentNewEndpoint<Demand>,
        GetByIdEndpoint<Demand>,
        PutByIdEndpoint<Demand>,
        MassCreateUpdateDeleteEndpoint<Demand>,
        DocumentPositionsEndpoint<DemandDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<Demand> {

    public DemandClient(ApiClient api) {
        super(api, "/entity/demand/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Demand.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }

    @Override
    public Class<DemandDocumentPosition> documentPositionClass() {
        return DemandDocumentPosition.class;
    }
}
