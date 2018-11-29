package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.ServiceEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedPriceTypesResponse;

public final class ServiceClient
        extends ApiClient
        implements
        GetListEndpoint<ServiceEntity>,
        PostEndpoint<ServiceEntity>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedPriceTypesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<ServiceEntity>,
        PutByIdEndpoint<ServiceEntity> {

    public ServiceClient(LognexApi api) {
        super(api, "/entity/service/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ServiceEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedPriceTypesResponse.class;
    }
}
