package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.MetadataAttributeEndpoint;
import com.lognex.api.clients.endpoints.MetadataEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailShift;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class RetailShiftClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<RetailShift>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint {

    public RetailShiftClientEntity(ApiClient api) {
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
