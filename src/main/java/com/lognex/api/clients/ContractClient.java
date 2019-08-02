package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.Contract;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class ContractClient
        extends EntityClientBase
        implements
        GetListEndpoint<Contract>,
        PostEndpoint<Contract>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Contract>,
        PutByIdEndpoint<Contract> {

    public ContractClient(ApiClient api) {
        super(api, "/entity/contract/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Contract.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
