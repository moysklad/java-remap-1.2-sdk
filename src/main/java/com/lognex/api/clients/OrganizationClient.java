package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.AgentAccount;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public final class OrganizationClient
        extends EntityClientBase
        implements
        GetListEndpoint<Organization>,
        PostEndpoint<Organization>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Organization>,
        PutByIdEndpoint<Organization> {

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
