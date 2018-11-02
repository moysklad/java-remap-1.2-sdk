package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;

public final class GroupClient
        extends ApiClient
        implements
        GetListEndpoint<GroupEntity>,
        GetByIdEndpoint<GroupEntity>,
        PostEndpoint<GroupEntity> {

    public GroupClient(LognexApi api) {
        super(api, "/entity/group/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return GroupEntity.class;
    }
}
