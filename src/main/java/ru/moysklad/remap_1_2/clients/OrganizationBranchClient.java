package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.agents.OrganizationBranch;

public final class OrganizationBranchClient
        extends EntityClientBase
        implements
        GetListEndpoint<OrganizationBranch>,
        PostEndpoint<OrganizationBranch>,
        DeleteByIdEndpoint,
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
        return OrganizationBranch.class;
    }
}
