package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.State;

public class StateClient
        extends EntityClientBase
        implements
        GetByIdEndpoint<State>,
        PostEndpoint<State>,
        PutByIdEndpoint<State>,
        MassCreateUpdateEndpoint<State>,
        DeleteByIdEndpoint
{

    public StateClient(ApiClient api, String path) {
        super(api, path + "metadata/states/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return State.class;
    }
}
