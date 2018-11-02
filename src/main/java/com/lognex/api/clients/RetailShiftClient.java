package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.MetadataAttributeEndpoint;
import com.lognex.api.clients.endpoints.MetadataEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RetailShiftEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class RetailShiftClient
        extends ApiClient
        implements
        GetListEndpoint<RetailShiftEntity>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint {

    public RetailShiftClient(LognexApi api) {
        super(api, "/entity/retailshift/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailShiftEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
