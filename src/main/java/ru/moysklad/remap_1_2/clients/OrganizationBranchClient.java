package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.agents.OrganizationBranch;
import ru.moysklad.remap_1_2.clients.endpoints.DeleteByIdEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.GetByIdEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.GetListEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.MassCreateUpdateDeleteEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.MetadataEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.PostEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.PutByIdEndpoint;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;

public final class OrganizationBranchClient
        extends EntityClientBase
        implements
        GetListEndpoint<OrganizationBranch>,
        PostEndpoint<OrganizationBranch>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse<Attribute>>,
        GetByIdEndpoint<OrganizationBranch>,
        PutByIdEndpoint<OrganizationBranch>,
        MassCreateUpdateDeleteEndpoint<OrganizationBranch> {

    public OrganizationBranchClient(ApiClient api) {
        super(api, "/entity/organizationbranch/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return OrganizationBranch.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedResponse.class;
    }
}
