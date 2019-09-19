package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.Move;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class MoveClient
        extends EntityClientBase
        implements
        GetListEndpoint<Move>,
        PostEndpoint<Move>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<Move>,
        GetByIdEndpoint<Move>,
        PutByIdEndpoint<Move>,
        DocumentPositionsEndpoint,
        ExportEndpoint,
        PublicationEndpoint {

    public MoveClient(ApiClient api) {
        super(api, "/entity/move/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Move.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
