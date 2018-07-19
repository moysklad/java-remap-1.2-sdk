package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.agents.OrganizationEntity;

public final class OrganizationClient
        extends ApiClient
        implements
        GetListEndpoint<OrganizationEntity>,
        PostEndpoint<OrganizationEntity> {

    public OrganizationClient(LognexApi api) {
        super(api, "/entity/organization", OrganizationEntity.class);
    }
}
