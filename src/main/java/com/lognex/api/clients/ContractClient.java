package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.ContractEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class ContractClient
        extends ApiClient
        implements
        GetListEndpoint<ContractEntity>,
        PostEndpoint<ContractEntity>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<ContractEntity>,
        PutByIdEndpoint<ContractEntity> {

    public ContractClient(LognexApi api) {
        super(api, "/entity/contract/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ContractEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
