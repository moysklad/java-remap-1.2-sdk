package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.FactureIn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class FactureInClient
        extends EntityClientBase
        implements
        GetListEndpoint<FactureIn>,
        PostEndpoint<FactureIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<FactureIn>,
        GetByIdEndpoint<FactureIn>,
        PutByIdEndpoint<FactureIn>,
        ExportEndpoint,
        PublicationEndpoint {

    public FactureInClient(ApiClient api) {
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
