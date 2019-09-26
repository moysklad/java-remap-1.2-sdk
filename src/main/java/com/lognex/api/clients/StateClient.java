package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.clients.endpoints.PutByIdEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.State;

public class StateClient
        extends EntityClientBase
        implements
        GetByIdEndpoint<State>,
        PostEndpoint<State>,
        PutByIdEndpoint<State>,
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
