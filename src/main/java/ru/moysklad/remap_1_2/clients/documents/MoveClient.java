package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.Move;
import ru.moysklad.remap_1_2.entities.documents.positions.MoveDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeOperationSharedStatesResponse;

public final class MoveClient
        extends EntityClientBase
        implements
        GetListEndpoint<Move>,
        PostEndpoint<Move>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeOperationSharedStatesResponse>,
        MetadataAttributeOperationEndpoint,
        DocumentNewEndpoint<Move>,
        GetByIdEndpoint<Move>,
        PutByIdEndpoint<Move>,
        MassCreateUpdateDeleteEndpoint<Move>,
        DocumentPositionsEndpoint<MoveDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<Move> {

    public MoveClient(ApiClient api) {
        super(api, "/entity/move/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Move.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeOperationSharedStatesResponse.class;
    }

    @Override
    public Class<MoveDocumentPosition> documentPositionClass() {
        return MoveDocumentPosition.class;
    }
}
