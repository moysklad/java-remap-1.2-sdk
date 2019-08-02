package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Uom;

public final class UomClient
        extends EntityClientBase
        implements
        GetListEndpoint<Uom>,
        PostEndpoint<Uom>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<Uom>,
        PutByIdEndpoint<Uom> {

    public UomClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/uom/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Uom.class;
    }
}
