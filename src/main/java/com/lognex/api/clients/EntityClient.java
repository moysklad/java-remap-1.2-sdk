package com.lognex.api.clients;

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

    public DocumentSupplyClient supply() {
        return new DocumentSupplyClient(api);
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

    public VariantByIdClient variant(String id) {
        return new VariantByIdClient(api, id);
    }

    public CurrencyClient currency() {
        return new CurrencyClient(api);
    }

    public DiscountClient discount() {
        return new DiscountClient(api);
    }

    public ContractClient contract() {
        return new ContractClient(api);
    }

    public ProductFolderClient productfolder() {
        return new ProductFolderClient(api);
    }

    public ServiceClient service() {
        return new ServiceClient(api);
    }

    public BundleClient bundle() {
        return new BundleClient(api);
    }

    public UomClient uom() {
        return new UomClient(api);
    }
}
