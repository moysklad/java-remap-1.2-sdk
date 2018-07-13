package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteEndpoint;
import com.lognex.api.clients.endpoints.GetEndpoint;
import com.lognex.api.clients.endpoints.PutEndpoint;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.products.BundleEntity;

public final class CounterpartyByIdClient
        extends ApiClient
        implements
        GetEndpoint<CounterpartyEntity>,
        DeleteEndpoint,
        PutEndpoint<CounterpartyEntity> {

    public CounterpartyByIdClient(LognexApi api, String id) {
        super(api, "/entity/counterparty/" + id, BundleEntity.class);
    }
}
