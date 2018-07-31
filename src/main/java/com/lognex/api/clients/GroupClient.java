package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.GroupEntity;

public final class GroupClient
        extends ApiClient
        implements
        GetListEndpoint<GroupEntity>,
        PostEndpoint<GroupEntity> {

    public GroupClient(LognexApi api) {
        super(api, "/entity/group/", GroupEntity.class);
    }
}
