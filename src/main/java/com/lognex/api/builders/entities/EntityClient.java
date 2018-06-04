package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;

public final class EntityClient {
    private final LognexApi api;

    public EntityClient(LognexApi api) {
        this.api = api;
    }

    public CounterpartyClient counterparty() {
        return new CounterpartyClient(api);
    }

    public CounterpartyByIdClient counterparty(String id) {
        return new CounterpartyByIdClient(api, id);
    }

    public OrganizationClient organization() {
        return new OrganizationClient(api);
    }

    public DocumentCustomerOrderClient customerorder() {
        return new DocumentCustomerOrderClient(api);
    }

    public DocumentCustomerOrderByIdClient customerorder(String id) {
        return new DocumentCustomerOrderByIdClient(api, id);
    }

    public DocumentDemandClient demand() {
        return new DocumentDemandClient(api);
    }

    public StoreClient store() {
        return new StoreClient(api);
    }

    public ProductClient product() {
        return new ProductClient(api);
    }

    public VariantClient variant() {
        return new VariantClient(api);
    }

    public CurrencyClient currency() {
        return new CurrencyClient(api);
    }

    public ContractClient contract() {
        return new ContractClient(api);
    }
}
