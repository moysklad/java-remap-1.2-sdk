package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.FactureIn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentFactureInClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<FactureIn>,
        PostEndpoint<FactureIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<FactureIn>,
        GetByIdEndpoint<FactureIn>,
        PutByIdEndpoint<FactureIn>,
        ExportEndpoint {

    public DocumentFactureInClientEntity(ApiClient api) {
        super(api, "/entity/facturein/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return FactureIn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
