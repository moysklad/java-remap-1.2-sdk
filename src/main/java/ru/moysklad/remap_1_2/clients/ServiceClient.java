package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.products.Service;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeResponse;

public final class ServiceClient
        extends EntityClientBase
        implements
        GetListEndpoint<Service>,
        PostEndpoint<Service>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeResponse>,
        GetByIdEndpoint<Service>,
        PutByIdEndpoint<Service>,
        MassCreateUpdateDeleteEndpoint<Service>,
        GetBySyncIdEndpoint<Service> {

    public ServiceClient(ru.moysklad.remap_1_2.ApiClient api) {
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
