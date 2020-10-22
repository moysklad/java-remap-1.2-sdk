package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.clients.endpoints.PutByIdEndpoint;
import com.lognex.api.entities.Group;
import com.lognex.api.entities.MetaEntity;

public final class GroupClient
        extends EntityClientBase
        implements
        GetListEndpoint<Group>,
        GetByIdEndpoint<Group>,
        PostEndpoint<Group>,
        PutByIdEndpoint<Group>,
        DeleteByIdEndpoint {

    public GroupClient(ApiClient api) {
        super(api, "/entity/group/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Group.class;
    }
}
