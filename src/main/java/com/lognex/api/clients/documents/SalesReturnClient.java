package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.SalesReturn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class SalesReturnClient
        extends EntityClientBase
        implements
        GetListEndpoint<SalesReturn>,
        PostEndpoint<SalesReturn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<SalesReturn>,
        GetByIdEndpoint<SalesReturn>,
        PutByIdEndpoint<SalesReturn>,
        DocumentPositionsEndpoint,
        ExportEndpoint,
        PublicationEndpoint {

    public SalesReturnClient(ApiClient api) {
        super(api, "/entity/salesreturn/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return SalesReturn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
