package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.Loss;
import com.lognex.api.entities.documents.positions.LossDocumentPosition;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class LossClient
        extends EntityClientBase
        implements
        GetListEndpoint<Loss>,
        PostEndpoint<Loss>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<Loss>,
        GetByIdEndpoint<Loss>,
        PutByIdEndpoint<Loss>,
        MassCreateUpdateDeleteEndpoint<Loss>,
        DocumentPositionsEndpoint<LossDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public LossClient(ApiClient api) {
        super(api, "/entity/loss/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Loss.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }

    @Override
    public Class<LossDocumentPosition> documentPositionClass() {
        return LossDocumentPosition.class;
    }
}
