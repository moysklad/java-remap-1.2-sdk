package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.products.Service;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedPriceTypesResponse;

public final class ServiceClient
        extends EntityClientBase
        implements
        GetListEndpoint<Service>,
        PostEndpoint<Service>,
        DeleteByIdEndpoint,
        ProductMetadataEndpoint,
        ProductMetadataAttributeEndpoint,
        GetByIdEndpoint<Service>,
        PutByIdEndpoint<Service>,
        MassCreateUpdateDeleteEndpoint<Service>,
        GetBySyncIdEndpoint<Service>,
        HasFilesEndpoint<Service> {

    public ServiceClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/service/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Service.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedPriceTypesResponse.class;
    }
}
