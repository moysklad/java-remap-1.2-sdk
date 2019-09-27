package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.PrepaymentReturn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class PrepaymentReturnClient
        extends EntityClientBase
        implements
        GetListEndpoint<PrepaymentReturn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<PrepaymentReturn>,
        GetByIdEndpoint<PrepaymentReturn>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public PrepaymentReturnClient(ApiClient api) {
        super(api, "/entity/prepaymentreturn/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PrepaymentReturn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
