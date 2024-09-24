package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.Loss;
import ru.moysklad.remap_1_2.entities.documents.positions.LossDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeOperationSharedStatesResponse;

public final class LossClient
        extends EntityClientBase
        implements
        GetListEndpoint<Loss>,
        PostEndpoint<Loss>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeOperationSharedStatesResponse>,
        MetadataAttributeOperationEndpoint,
        DocumentNewEndpoint<Loss>,
        GetByIdEndpoint<Loss>,
        PutByIdEndpoint<Loss>,
        MassCreateUpdateDeleteEndpoint<Loss>,
        DocumentPositionsEndpoint<LossDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<Loss> {

    public LossClient(ApiClient api) {
        super(api, "/entity/loss/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Loss.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeOperationSharedStatesResponse.class;
    }

    @Override
    public Class<LossDocumentPosition> documentPositionClass() {
        return LossDocumentPosition.class;
    }
}
