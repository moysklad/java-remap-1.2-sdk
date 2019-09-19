package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Region;

public final class RegionClient
        extends EntityClientBase
        implements
        GetListEndpoint<Region>,
        GetByIdEndpoint<Region> {

    public RegionClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/region/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Region.class;
    }
}
