package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.MetadataAttributeEndpoint;
import com.lognex.api.clients.endpoints.MetadataEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.ProjectEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;

public final class ProjectClient
        extends ApiClient
        implements
        GetListEndpoint<ProjectEntity>,
        PostEndpoint<ProjectEntity>,
        MetadataEndpoint<MetadataAttributeSharedResponse>,
        MetadataAttributeEndpoint {

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
