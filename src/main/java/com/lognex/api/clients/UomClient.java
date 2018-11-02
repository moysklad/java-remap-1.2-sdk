package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.UomEntity;

public final class UomClient
        extends ApiClient
        implements
        GetListEndpoint<UomEntity>,
        PostEndpoint<UomEntity> {

    public UomClient(LognexApi api) {
        super(api, "/entity/uom/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return UomEntity.class;
    }
}
