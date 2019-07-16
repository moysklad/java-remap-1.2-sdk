package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Project;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;

public final class ProjectClient
        extends com.lognex.api.clients.ApiClient
        implements
        GetListEndpoint<Project>,
        PostEndpoint<Project>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Project>,
        PutByIdEndpoint<Project> {

    public ProjectClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/project/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Project.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedResponse.class;
    }
}
