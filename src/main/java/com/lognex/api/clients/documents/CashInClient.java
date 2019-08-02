package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.CashIn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class CashInClient
        extends EntityClientBase
        implements
        GetListEndpoint<CashIn>,
        PostEndpoint<CashIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<CashIn>,
        GetByIdEndpoint<CashIn>,
        PutByIdEndpoint<CashIn>,
        ExportEndpoint {

    public CashInClient(ApiClient api) {
        super(api, "/entity/cashin/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CashIn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
