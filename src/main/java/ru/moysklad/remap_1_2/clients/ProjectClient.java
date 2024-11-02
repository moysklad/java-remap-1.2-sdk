package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.Project;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;

public final class ProjectClient
        extends EntityClientBase
        implements
        GetListEndpoint<Project>,
        PostEndpoint<Project>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse<Attribute>>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Project>,
        MassCreateUpdateDeleteEndpoint<Project>,
        PutByIdEndpoint<Project> {

    public ProjectClient(ru.moysklad.remap_1_2.ApiClient api) {
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
