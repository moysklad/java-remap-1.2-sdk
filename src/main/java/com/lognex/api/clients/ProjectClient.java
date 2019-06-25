package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.ProjectEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;

public final class ProjectClient
        extends ApiClient
        implements
        GetListEndpoint<ProjectEntity>,
        PostEndpoint<ProjectEntity>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<ProjectEntity>,
        PutByIdEndpoint<ProjectEntity> {

    public ProjectClient(LognexApi api) {
        super(api, "/entity/project/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProjectEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedResponse.class;
    }
}
