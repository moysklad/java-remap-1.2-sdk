package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.ContractEntity;

public final class ContractClient
        extends ApiClient
        implements
        GetListEndpoint<ContractEntity>,
        PostEndpoint<ContractEntity> {

    public ContractClient(LognexApi api) {
        super(api, "/entity/contract", ContractEntity.class);
    }
}
