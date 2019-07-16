package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.AgentAccount;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;
import java.util.Collection;

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
    public ListEntity<AgentAccount> getAccounts(String organizationId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + organizationId + "/accounts").
                apiParams(params).
                list(AgentAccount.class);
    }

    @ApiEndpoint
    public void createAccounts(String organizationId, Collection<AgentAccount> entities) throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api(), path() + organizationId + "/accounts").
                body(entities).
                post();
    }
}
