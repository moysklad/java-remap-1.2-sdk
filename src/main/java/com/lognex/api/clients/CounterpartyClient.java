package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.GetMetadataEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.responses.CounterpartyMetadataListResponse;

public final class CounterpartyClient
        extends ApiClient
        implements
        GetListEndpoint<CounterpartyEntity>,
        PostEndpoint<CounterpartyEntity>,
        GetMetadataEndpoint<CounterpartyMetadataListResponse> {

    public CounterpartyClient(LognexApi api) {
        super(
                api, "/entity/counterparty",
                CounterpartyEntity.class,
                CounterpartyMetadataListResponse.class
        );
    }
}
