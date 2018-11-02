package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.AccountEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;
import java.util.Collection;

public final class OrganizationClient
        extends ApiClient
        implements
        GetListEndpoint<OrganizationEntity>,
        PostEndpoint<OrganizationEntity>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<OrganizationEntity>,
        PutByIdEndpoint<OrganizationEntity> {

    public OrganizationClient(LognexApi api) {
        super(api, "/entity/organization/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return OrganizationEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedResponse.class;
    }

    @ApiEndpoint
    public ListEntity<AccountEntity> getAccounts(String organizationId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + organizationId + "/accounts").
                apiParams(params).
                list(AccountEntity.class);
    }

    @ApiEndpoint
    public void postAccounts(Collection<AccountEntity> entities) throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api(), path()).
                body(entities).
                post();
    }
}
