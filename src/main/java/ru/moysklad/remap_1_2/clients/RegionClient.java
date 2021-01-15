package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.GetByIdEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.GetListEndpoint;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.Region;

public final class RegionClient
        extends EntityClientBase
        implements
        GetListEndpoint<Region>,
        GetByIdEndpoint<Region> {

    public RegionClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/region/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Region.class;
    }
}
