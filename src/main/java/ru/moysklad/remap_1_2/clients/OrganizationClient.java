package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.AgentAccount;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public final class OrganizationClient
        extends EntityClientBase
        implements
        GetListEndpoint<Organization>,
        PostEndpoint<Organization>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse<Attribute>>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Organization>,
        PutByIdEndpoint<Organization>,
        MassCreateUpdateDeleteEndpoint<Organization> {

    public OrganizationClient(ApiClient api) {
        super(api, "/entity/organization/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Organization.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedResponse.class;
    }

    @ApiEndpoint
    public ListEntity<AgentAccount> getAccounts(String organizationId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + organizationId + "/accounts").
                apiParams(params).
                list(AgentAccount.class);
    }

    @ApiEndpoint
    public ListEntity<AgentAccount> getAccounts(Organization organization, ApiParam... params) throws IOException, ApiClientException {
        return getAccounts(organization.getId(), params);
    }

    @ApiEndpoint
    public List<AgentAccount> createAccounts(String organizationId, Collection<AgentAccount> entities) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + organizationId + "/accounts").
                body(entities).
                postList(AgentAccount.class);
    }

    @ApiEndpoint
    public List<AgentAccount> createAccounts(Organization organization, Collection<AgentAccount> entities) throws IOException, ApiClientException {
        return createAccounts(organization.getId(), entities);
    }
}
