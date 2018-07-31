package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.ProjectEntity;

public final class ProjectClient
        extends ApiClient
        implements
        GetListEndpoint<ProjectEntity>,
        PostEndpoint<ProjectEntity> {

    public ProjectClient(LognexApi api) {
        super(api, "/entity/project/", ProjectEntity.class);
    }
}
