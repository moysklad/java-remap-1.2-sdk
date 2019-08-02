package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.Enter;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class EnterClient
        extends EntityClientBase
        implements
        GetListEndpoint<Enter>,
        PostEndpoint<Enter>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Enter>,
        PutByIdEndpoint<Enter>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public EnterClient(ApiClient api) {
        super(api, "/entity/enter/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Enter.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
