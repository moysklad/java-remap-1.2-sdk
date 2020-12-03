package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailShift;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class RetailShiftClient
        extends EntityClientBase
        implements
        GetListEndpoint<RetailShift>,
        DeleteByIdEndpoint,
        MetadataAttributeEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        ExportEndpoint,
        PublicationEndpoint {

    public RetailShiftClient(ApiClient api) {
        super(api, "/entity/retailshift/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailShift.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
