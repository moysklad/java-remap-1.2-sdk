package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.Service;
import com.lognex.api.responses.metadata.MetadataAttributeResponse;

public final class ServiceClient
        extends EntityClientBase
        implements
        GetListEndpoint<Service>,
        PostEndpoint<Service>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Service>,
        PutByIdEndpoint<Service>,
        MassCreateUpdateDeleteEndpoint<Service>,
        GetBySyncIdEndpoint<Service> {

    public ServiceClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/service/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Service.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeResponse.class;
    }
}
