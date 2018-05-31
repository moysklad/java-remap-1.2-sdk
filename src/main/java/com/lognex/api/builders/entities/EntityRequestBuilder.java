package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;

public final class EntityRequestBuilder {
    private final LognexApi api;

    public EntityRequestBuilder(LognexApi api) {
        this.api = api;
    }

    public CounterpartyRequestBuilder counterparty() {
        return new CounterpartyRequestBuilder(api);
    }

    public CounterpartyByIdRequestBuilder counterparty(String id) {
        return new CounterpartyByIdRequestBuilder(api, id);
    }

    public OrganizationRequestBuilder organization() {
        return new OrganizationRequestBuilder(api);
    }

    public DocumentCustomerOrderRequestBuilder customerorder() {
        return new DocumentCustomerOrderRequestBuilder(api);
    }

    public DocumentCustomerOrderByIdRequestBuilder customerorder(String id) {
        return new DocumentCustomerOrderByIdRequestBuilder(api, id);
    }

    public DocumentDemandRequestBuilder demand() {
        return new DocumentDemandRequestBuilder(api);
    }

    public StoreRequestBuilder store() {
        return new StoreRequestBuilder(api);
    }

    public ProductRequestBuilder product() {
        return new ProductRequestBuilder(api);
    }

    public VariantRequestBuilder variant() {
        return new VariantRequestBuilder(api);
    }

    public CurrencyRequestBuilder currency() {
        return new CurrencyRequestBuilder(api);
    }

    public ContractRequestBuilder contract() {
        return new ContractRequestBuilder(api);
    }
}
